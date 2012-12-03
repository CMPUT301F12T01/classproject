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

import java.util.HashMap;
import java.util.UUID;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * This class allows for the retrieval of Tasks
 * from the database
 * 
 * @author Neil
 *
 */
public class TaskLocalRetrieval {
	
	/**
	 * Get all of the Tasks that have a particular
	 * scope from the Database. 
	 * 
	 * @param SQLiteDatabase db
	 * @param boolean global
	 * @return ArrayList<Task>
	 */
	public static HashMap<UUID, Task> getTasks(SQLiteDatabase db, String userid, boolean global) {
		
		String taskTable = "Tasks";
		String requestTable = "Requests";
		String []taskSelectColumns = {"userid", "id", "global", "summary", "description"};
		String []requestSelectColumns = {"task_id", "description", "quantity", "mediatype"};
		HashMap<UUID, Task> taskHash = new HashMap<UUID, Task>();
		
		Cursor scopedTasks;
		
		if (userid != null) {
			scopedTasks = db.query(taskTable, 
					taskSelectColumns, taskSelectColumns[0] + " = '" + userid + "'", 
					null, null, null, null);
		}
		else if (global) {
			scopedTasks = db.query(taskTable, 
					taskSelectColumns, taskSelectColumns[2] + " = " + 1, 
					null, null, null, null);
		} else {
			scopedTasks = db.query(taskTable, 
					taskSelectColumns, taskSelectColumns[2] + " = " + 0, 
					null, null, null, null);
		}
		
		for (scopedTasks.moveToFirst(); !scopedTasks.isAfterLast(); scopedTasks.moveToNext()) {
			Task newTask = new Task(
					UUID.fromString(scopedTasks.getString(
							scopedTasks.getColumnIndex(taskSelectColumns[1]))), 
					scopedTasks.getString(
							scopedTasks.getColumnIndex(taskSelectColumns[0])));
			
			newTask.setSummary(scopedTasks.getString(
					scopedTasks.getColumnIndex(taskSelectColumns[3])));
			newTask.setDescription(scopedTasks.getString(
					scopedTasks.getColumnIndex(taskSelectColumns[4])));
			if (global) {
				newTask.setGlobal();
			} else {
				newTask.setLocal();
			}
			
			// GET ALL REQUESTS ASSOCIATED WITH THE TASK
			Cursor taskRequests = db.query(requestTable, requestSelectColumns, 
					requestSelectColumns[0] + " = '" + scopedTasks.getString(
							scopedTasks.getColumnIndex(taskSelectColumns[1])) + "'", 
					null, null, null, null);
			
			for (taskRequests.moveToFirst(); !taskRequests.isAfterLast(); taskRequests.moveToNext()) {
				
				Request newRequest = new Request(MediaType.valueOf(taskRequests.getString(
						taskRequests.getColumnIndex(requestSelectColumns[3]))));
				newRequest.setDescription(taskRequests.getString(
						taskRequests.getColumnIndex(requestSelectColumns[1])));
				newRequest.setQuantity(taskRequests.getInt(
						taskRequests.getColumnIndex(requestSelectColumns[2])));
				newTask.addRequest(newRequest);
			}

			taskHash.put(newTask.getId(), newTask);
		}
		
		return taskHash;
	}

}
