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
    
    /* Always instantiate with these collections. */
    {
        allCollections.put("local", localTasks);
        allCollections.put("global", globalTasks);
    }

    // our singleton instance
    private static TaskManager singleton = null;


    /**
     * Initialize singleton TaskManager.
     */
    private TaskManager(Collection<Task> initialLocalTasks, Collection<Task> intitialGlobalTasks) {
        localTasks = new TaskCollection(initialLocalTasks);
        globalTasks = new TaskCollection(intitialGlobalTasks);
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
     * given a UUID, return the task
     * 
     * @param id
     *            ID of task we are looking for
     * @return Task that matches ID
     */
    public Task get(UUID id) {

        /* Look in every collection to see if we can find the task. */
        /* TODO: should probably prioritize local storage. */
        for (TaskCollection collection : allCollections.values()) {
            
            if (collection == null)
                continue;
            
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
    
    public static TaskManager initializeTaskMananger(Collection<Task> initialLocalTasks, Collection<Task> intitialGlobalTasks) {
        singleton = new TaskManager(initialLocalTasks, intitialGlobalTasks);
        
        return singleton;

    }

}
