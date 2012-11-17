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
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * Class to interface between the model and the storage
 *
 *@author Mitchell Home
 *@author Neil Borle
 */

// TODO: Javadoc the rest of this interface.

public interface StorageInterface {
	
	// Storage and retrieval of Tasks
	public void storeTask(Task taskToStore);
	
	public ArrayList<Task> getOwnTasks(UUID userid);
	
	public ArrayList<Task> getLocalTasks();
	
	public ArrayList<Task> getGlobalTasks();
	
	// Storage and retrieval of reports
	public void storeReport(Report reportToStore);
	
	public ArrayList<Report> getLocalReports(UUID taskid);
	
	public ArrayList<Report> getTaskCreatorReports(UUID taskid);
	
	public ArrayList<Report> getGlobalReports(UUID taskid);

}
