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

import ca.ualberta.cs.c301f12t01.common.Task;

/*Class to mangage all of our tasks
 * @author Mitchell Home
 */
public class TaskManager extends Observable{
	//our collections
	private TaskCollection localTasks;
	private TaskCollection globalTasks;
	//our instance
	private static final TaskManager instance =	new TaskManager();

	//private constructor
	private TaskManager(){
		//nothing to do really
	}

	//adds a new task
	//puts in either localTasks or globalTasks
	public void addTask(Task newTask){
		if (Task.isLocal()){
			localTasks.add(newTask);
		}
		else if (Task.isGlobal()){
			globalTasks.add(newTask);
		}
		else{
			//handle errors??
		}
		//notify that we changed
		notifyObservers();
	}

	//returns iterator for all local tasks
	public Iterator<Task> getLocalTasks(){
		return localTasks.iterator();
	}

	//returns iterator for all global tasks
	public Iterator<Task> getGlobalTasks(){
		return globalTasks.iterator();
	}
}
