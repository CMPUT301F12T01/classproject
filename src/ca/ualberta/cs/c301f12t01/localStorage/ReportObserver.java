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
package ca.ualberta.cs.c301f12t01.localStorage;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Report;

import ca.ualberta.cs.c301f12t01.model.StorageInterface;
import ca.ualberta.cs.c301f12t01.model.TaskManager;

/**
 * 
 * @author home
 *
 */
public class ReportObserver implements Observer{
	//where we store things
	private StorageInterface localStorage;
	private TaskManager tm = null;
	//private StorageInterface serverStorage;
	/**TODO Implement the server stuff */

	public ReportObserver(TaskManager tm){
		this.tm = tm;
	}
	
	/**
	 * 
	 * @param localStorage
	 * 			Local storage we got passed
	 */
	public void setLocal(StorageInterface localStorage){
		this.localStorage = localStorage;
	}
	
	/**
	 * 
	 * @param globalStorage
	 * 			Global storage we got passed
	 */
	public void setServer(StorageInterface serverStorage){
		//this.serverStorage = serverStorage;
	}
	
	/**
	 * Updates
	 */
	public void update(Observable obsv, Object arg) {
		if (arg instanceof Report){
			Report newTask = (Report) arg;
			localStorage.storeReport(newTask);
		}
		else{
			//Something is very very wrong
		}
	}
}
