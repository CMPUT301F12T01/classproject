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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Task;

/* TODO: HashMaps. USE THEM. */

/**
 * Class to manage all of our tasks Singleton design pattern
 * 
 * @author Mitchell Home
 */
public class TaskManager extends Observable {
	// our collections
	private List<Task> localTasks = new ArrayList<Task>();
	private List<Task> globalTasks = new ArrayList<Task>();

	// our instance
	private static final TaskManager instance = new TaskManager();

	// our StorageInterface

	/** TODO Implement server stuff */

	/**
	 * private constructor
	 */
	private TaskManager() {
		// nothing to do really
	}

	/**
	 * adds a new task into either localTasks or globalTasks
	 * 
	 * @param newTask
	 *            task to be added
	 */
	public void addTask(Task newTask) {
		if (newTask.isLocal()) {
			localTasks.add(newTask);

		} else if (newTask.isGlobal()) {
			globalTasks.add(newTask);

		} else {
			// handle errors??
		}
		// notify that we changed
		setChanged();
		notifyObservers(newTask);
	}

	/**
	 * given a UUID, return the task
	 * 
	 * @param id
	 *            ID of task we are looking for
	 * @return Task that matches ID
	 */
	public Task get(UUID id) {

		// This is a little ugly
		// first check local tasks
		List<Task> l = getLocalTasks();
		Iterator<Task> i = l.iterator();
		while (i.hasNext()) {
			Task t = i.next();

			if (t.getId().equals(id)) {

				return t;
			}
		}
		// If we didn't find it in our local tasks
		// check our global tasks
		l = getGlobalTasks();
		i = l.iterator();
		List<Task> g = getGlobalTasks();
		i = g.iterator();
		while (i.hasNext()) {
			Task t = i.next();
			if (t.getId().equals(id)) {

				return t;
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
	public List<Task> getLocalTasks() {
		return localTasks;
	}

	/**
	 * returns all global tasks
	 * 
	 * @return ArrayList of local tasks
	 */
	public List<Task> getGlobalTasks() {
		return globalTasks;
	}

	/**
	 * let them get our instance
	 * 
	 * @return Singleton instance of TaskManager
	 */
	public static TaskManager getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param localStorage
	 *            Where our local storage is
	 */
	public void loadTasks(StorageInterface localStorage) {
		localTasks = (List<Task>) localStorage.getLocalTasks();
		/* TODO Make Neil use Lists and not collections */
	}

}
