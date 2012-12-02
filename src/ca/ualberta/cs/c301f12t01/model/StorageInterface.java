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
import java.util.HashMap;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * Class to interface between the model and the storage
 *
 * Refactored to return HashMaps and take Tasks instead
 * of taking UUIDs
 *
 *@author Mitchell Home
 *@author Neil Borle
 */

// TODO: Javadoc the rest of this interface.

public interface StorageInterface {
	
	// Handle closing the Database
	public void close();
	
	// Storage and retrieval of Tasks
	public void storeTask(Task taskToStore);
	
	public void updateTask(Task taskToUpdate);
	
	public void removeTask(Task taskToRemove);
	
	// wtf, neil
	//public HashMap<UUID, Task> getOwnTasks(String userid);
	
	public HashMap<UUID, Task> getLocalTasks();
	
	public HashMap<UUID, Task> getGlobalTasks();
	
	// Storage and retrieval of reports
	public void storeReport(Report reportToStore);
	
	public void updateReport(Report reportToUpdate);
	
	public void removeReport(Report reportToRemove);
	
	public ArrayList<Report> getAllReports();
	
	public ArrayList<Report> getReports(Task matchingTask);
	
	public ArrayList<Report> getLocalReports(Task matchingTask);
	
	public ArrayList<Report> getTaskCreatorReports(Task matchingTask);
	
	public ArrayList<Report> getGlobalReports(Task matchingTask);

}
