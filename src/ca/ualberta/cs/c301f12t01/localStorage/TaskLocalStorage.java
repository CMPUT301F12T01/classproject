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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * This class abstracts out the storage 
 * and retrieval of Tasks from Device storage
 * in the interest of flexible code
 * 
 * This assembles and disassembles the Tasks
 * and the associated requests
 * 
 * @author Neil Borle
 *
 */
public class TaskLocalStorage {

	/**
	 * Takes a Task and stores it to the local
	 * SQLiteDatabase
	 * 
	 * @param SQLiteDatabase db
	 * @param Task taskToStore
	 */
	public static void storeTask(SQLiteDatabase db, Task taskToStore) {
		/*
		 * Order of operations: first the task is decomposed into by it's
		 * attributes which are then stored in the database, then each
		 * request associated with the task is decomposed and stored in
		 * the data base
		 */
		String taskTable = "Tasks";
		String requestTable = "Requests";
		String []taskCollumns = {"userid", "id", "global", 
				"summary", "description"};
		String []reqCollumns = {"task_id", "description",
	    		"quantity", "amountfulfilled", "mediatype"};
		
		// Get all the fields from the task 
		String userId = taskToStore.getUser().toString();
		String id = taskToStore.getId().toString();
		int global = 0;
		String summary = taskToStore.getSummary();
		String description = taskToStore.getDescription();
		
		if (taskToStore.isGlobal()) {
			global = 1;
		}
		
		// store all the extracted fields into the database
		ContentValues taskValues = new ContentValues();
	    taskValues.put(taskCollumns[0], userId);
	    taskValues.put(taskCollumns[1], id);
	    taskValues.put(taskCollumns[2], global);
	    taskValues.put(taskCollumns[3], summary);
	    taskValues.put(taskCollumns[4], description);
	    
	    //TODO ERROR HANDLING IF db.insert returns -1
	    db.insert(taskTable, null, taskValues);
	    
	    for (Request request : taskToStore) {
	    	
	    	ContentValues requestValues = new ContentValues();
	    	requestValues.put(reqCollumns[0], taskToStore.getId().toString());
	    	requestValues.put(reqCollumns[1], request.getDescription());
	    	requestValues.put(reqCollumns[2], request.getQuantity());
	    	requestValues.put(reqCollumns[3], 0);
	    	requestValues.put(reqCollumns[4], request.getType().toString());
	    	db.insert(requestTable, null, requestValues);
	    	
	    }
	    
	}
	
	/**
	 * Get all of the Tasks that belong to a particular
	 * user from the Database
	 * 
	 * @param SQLiteDatabase db
	 * @param UUID userid
	 * @return ArrayList<Task>
	 */
	public static HashMap<UUID, Task> getOwnTasks(SQLiteDatabase db, UUID userid) {
		
		String taskTable = "Tasks";
		String requestTable = "Requests";
		String []taskSelectColumns = {"userid", "id", "global", "summary", "description"};
		String []requestSelectColumns = {"task_id", "description", "quantity", "mediatype"};
		
		HashMap<UUID, Task> taskHash = new HashMap<UUID, Task>();
		String user = userid.toString();
		
		Cursor userTasks = db.query(taskTable, 
				taskSelectColumns, taskSelectColumns[0] + " = '" + user + "'", 
				null, null, null, null);
		
		for (userTasks.moveToFirst(); !userTasks.isAfterLast(); userTasks.moveToNext()) {
			Task newTask = new Task(
					UUID.fromString(userTasks.getString(
							userTasks.getColumnIndex(taskSelectColumns[1]))), 
					UUID.fromString(userTasks.getString(
							userTasks.getColumnIndex(taskSelectColumns[0]))));
			
			newTask.setSummary(userTasks.getString(
					userTasks.getColumnIndex(taskSelectColumns[3])));
			newTask.setDescription(userTasks.getString(
					userTasks.getColumnIndex(taskSelectColumns[4])));
			
			if (userTasks.getInt(userTasks.getColumnIndex(taskSelectColumns[2])) == 1) {
				newTask.isGlobal();
			}
			else {
				newTask.isLocal();
			}
			
			// GET ALL REQUESTS ASSOCIATED WITH THE TASK
			Cursor taskRequests = db.query(requestTable, requestSelectColumns, 
					requestSelectColumns[0] + " = '" + userTasks.getString(
							userTasks.getColumnIndex(taskSelectColumns[1])) + "'", 
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
	
	/**
	 * Get all of the Tasks that have a particular
	 * scope from the Database. 
	 * 
	 * @param SQLiteDatabase db
	 * @param boolean global
	 * @return ArrayList<Task>
	 */
	public static HashMap<UUID, Task> getTasks(SQLiteDatabase db, boolean global) {
		
		String taskTable = "Tasks";
		String requestTable = "Requests";
		String []taskSelectColumns = {"userid", "id", "global", "summary", "description"};
		String []requestSelectColumns = {"task_id", "description", "quantity", "mediatype"};
		HashMap<UUID, Task> taskHash = new HashMap<UUID, Task>();
		
		Cursor scopedTasks;
		
		if (global) {
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
					UUID.fromString(scopedTasks.getString(
							scopedTasks.getColumnIndex(taskSelectColumns[0]))));
			
			newTask.setSummary(scopedTasks.getString(
					scopedTasks.getColumnIndex(taskSelectColumns[3])));
			newTask.setDescription(scopedTasks.getString(
					scopedTasks.getColumnIndex(taskSelectColumns[4])));
			if (global) {
				newTask.isGlobal();
			} else {
				newTask.isLocal();
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
