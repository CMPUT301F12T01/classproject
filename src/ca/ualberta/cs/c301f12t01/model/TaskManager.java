/**
 * Copyright 2012 Neil Borle, Mitchell Home, Bronte Lee, Aaron
 * Padlesky, Eddie Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ca.ualberta.cs.c301f12t01.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Task;

/* TODO: THIS MUST BE SUPER TESTED! */

/**
 * Class to manage all of our tasks Singleton design pattern
 * 
 * @author Mitchell Home
 * @author Eddie Antonio Santos
 */
public class TaskManager {
	// our collections
	private TaskCollection localTasks = null;
	private TaskCollection globalTasks = null;
	private HashMap<String, TaskCollection> allCollections = new HashMap<String, TaskCollection>();

	/** Key for local task collection. */
	static public final String LOCAL = "local";
	/** Key for global task collection. */
	static public final String GLOBAL = "global";

	// our singleton instance
	private static TaskManager singleton = null;

	/**
	 * Initialize singleton TaskManager.
	 */
	private TaskManager(Collection<Task> initialLocalTasks,
			Collection<Task> intitialGlobalTasks) {

		localTasks = new TaskCollection(initialLocalTasks);
		globalTasks = new TaskCollection(intitialGlobalTasks);

		/* Always instantiate the list with these collections: */
		allCollections.put(LOCAL, localTasks);
		allCollections.put(GLOBAL, globalTasks);
	}

	/**
	 * adds a new task into either localTasks or globalTasks
	 * 
	 * @param newTask
	 *            task to be added
	 */
	public void addTask(Task newTask) {
		TaskCollection appropriateCollection;

		/* Figure out which collection to dump the task in. */
		appropriateCollection = getCollectionForTask(newTask);

		appropriateCollection.add(newTask);
	}
	
	public void modifyTask(Task oldTask, Task newTask) {
		TaskCollection appropriateCollection;
		
		/* TODO: figure out if the task collections get changed and, remove if they have. */
		if (oldTask.isGlobal().equals(newTask.isGlobal())) {
			appropriateCollection = getCollectionForTask(oldTask);
			
			appropriateCollection.modify(oldTask, newTask);
		} else if (oldTask.isLocal().equals(newTask.isLocal())) {
			appropriateCollection = getCollectionForTask(oldTask);
			
			appropriateCollection.modify(oldTask, newTask);
		} else {
			TaskCollection appropriateOldCollection = getCollectionForTask(oldTask);
			TaskCollection appropriateNewCollection = getCollectionForTask(newTask);
			appropriateOldCollection.removeElement(oldTask);
			appropriateNewCollection.add(newTask);
		}
	}
	
	/**
	 * removes a task from its appropriate collection
	 * 
	 * @param task
	 */
	public void removeTask(Task task) {
		TaskCollection appropriateCollection;
		
		appropriateCollection = getCollectionForTask(task);
		
		appropriateCollection.remove(task);
	}

	/**
	 * Gets the appropriate TaskCollection for the sharing specified by the
	 * given Task.
	 */
	public TaskCollection getCollectionForTask(Task task) {

		if (task.isLocal()) {
			return localTasks;
		} else if (task.isGlobal()) {
			return globalTasks;
		} else {
			return null;
		}
	}

	/**
	 * Returns the TaskCollection that goes by the given name.
	 * 
	 * @param name
	 * @return The TaskCollection or null if it does not exist.
	 */
	public TaskCollection getCollectionByName(String name) {
		return allCollections.get(name);
	}

	/**
	 * Given a UUID, return the task
	 * 
	 * @param id
	 *            ID of task we are looking for
	 * @return Task that matches ID
	 */
	public Task get(UUID id) {

		/*
		 * Prioritize search in the local task collection. This will be the
		 * fastest look-up.
		 */
		{
			Task localTask = localTasks.get(id);

			if (localTask != null) {
				return localTask;
			}
		}

		/*
		 * If that fails, look in every collection to see if we can find the
		 * task.
		 */
		for (TaskCollection collection : allCollections.values()) {

			if (collection == null) {
				System.err.println("Found a null collection.");
				continue;
			}

			Task maybeTask = collection.get(id);
			if (maybeTask != null) {
				return maybeTask;
			}
		}

		// If we got here, we didn't find the task
		return null;

	}

	/**
	 * returns all local tasks
	 * 
	 * @return ArrayList of local tasks
	 */
	public TaskCollection getLocalTaskCollection() {
		return localTasks;
	}

	/**
	 * returns all global tasks
	 * 
	 * @return ArrayList of local tasks
	 */
	public TaskCollection getGlobalTaskCollection() {
		return globalTasks;
	}

	/**
	 * let them get our instance
	 * 
	 * @return Singleton instance of TaskManager
	 */
	public static TaskManager getInstance() {
		return singleton;
	}

	/** Sets up the singleton. class. */
	public static TaskManager initializeTaskMananger(
			Collection<Task> initialLocalTasks,
			Collection<Task> intitialGlobalTasks) {

		if (singleton == null) {
			singleton = new TaskManager(initialLocalTasks, intitialGlobalTasks);
		} else {
			System.err.println("WARNING: setting up the TaskManager twice.");

		}

		return singleton;

	}

}
