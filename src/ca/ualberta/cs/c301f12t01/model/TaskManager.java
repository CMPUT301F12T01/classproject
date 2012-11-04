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
import java.util.Iterator;
import java.util.Observable;
import java.util.UUID;

import android.content.Context;

import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.localStorage.DeviceStorage;

/**
 * Class to manage all of our tasks
 * Singleton design pattern
 * @author Mitchell Home
 */
public class TaskManager extends Observable{
	//our collections
	private TaskCollection localTasks = new TaskCollection();
	private TaskCollection globalTasks = new TaskCollection();

	//our instance
	private static final TaskManager instance =	new TaskManager();

	/**
	 * private constructor
	 */
	private TaskManager(){
		//nothing to do really
	}

	/**
	 * adds a new task into either localTasks or globalTasks
	 * @param newTask
	 * 			task to be added
	 */
	public void addTask(Task newTask){
		if (newTask.isLocal()){
			localTasks.add(newTask);
		}
		else if (newTask.isGlobal()){
			globalTasks.add(newTask);
		}
		else{
			//handle errors??
		}
		//notify that we changed
		notifyObservers();
	}
	
	/**
	 * given a UUID, return the task
	 * @param id
	 * 			ID of task we are looking for
	 * @return
	 * 			Task that matches ID
	 */
	public Task get(UUID id){
		//This is a little ugly
		//first check local tasks
		Iterator<Task> i = getLocalTasks();
		while (i.hasNext()){
			Task t = i.next();
			if (t.getId() == id){
				return t;
			}
		}
		//If we didn't find it in our local tasks
		//check our global tasks
		i = getGlobalTasks();
		while (i.hasNext()){
			Task t = i.next();
			if (t.getId() == id){
				return t;
			}
		}
		//If we got here, we didn't find the task
		return null;
		
	}
	
	/**
	 * returns iterator for all local tasks
	 * @return
	 * 		localTasks' Iterator
	 */
	public Iterator<Task> getLocalTasks(){
		return localTasks.iterator();
	}

	/**
	 * returns iterator for all global tasks
	 * @return
	 * 		globalTasks' Iterator
	 */	public Iterator<Task> getGlobalTasks(){
		return globalTasks.iterator();
	}
	
	/**
	 * let them get our instance
	 * @return
	 * 		Singleton instance of TaskManager
	 */
	public static TaskManager getInstance(){
		return instance;
	}
	
	/**
	 * 
	 * @param taskID
	 * 			Task that you want to get reports for
	 * @param ds
	 * 			DeviceStorage that stores our reports
	 * @return
	 * 			The Collection of reports associated with our task
	 */
	public Collection<Report> getReports(UUID taskID, DeviceStorage ds){
		return ds.getLocalReports(taskID);
	}
}
