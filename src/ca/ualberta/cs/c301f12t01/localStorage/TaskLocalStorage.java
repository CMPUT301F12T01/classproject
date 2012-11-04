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

import java.util.ArrayList;
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
 * @author Neil Borle
 *
 */
public class TaskLocalStorage {

	public static void storeTask(SQLiteDatabase db, Task taskToStore) {
		/**
		 * Order of operations: first the task is decomposed into by it's
		 * attributes which are then stored in the database, then each
		 * request associated with the task is decomposed and stored in
		 * the data base
		 */
		
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
	    taskValues.put("userid", userId);
	    taskValues.put("id", id);
	    taskValues.put("global", global);
	    taskValues.put("summary", summary);
	    taskValues.put("description", description);
	    
	    //TODO ERROR HANDLING IF db.insert returns -1
	    db.insert("Tasks", null, taskValues);
	    
	    for (Request request : taskToStore) {
	    	ContentValues requestValues = new ContentValues();
	    	requestValues.put("task_id", taskToStore.getId().toString());
	    	requestValues.put("description", request.getDescription());
	    	requestValues.put("quantity", request.getQuantity());
	    	requestValues.put("amountfulfilled", 0);
	    	requestValues.put("mediatype", request.getType().toString());
	    	db.insert("Requests", null, requestValues);
	    }
	    
	}
	
	public static ArrayList<Task> getOwnTasks(SQLiteDatabase db, UUID userid) {
		String user = userid.toString();
		String []taskSelectColumns = {"userid", "id", "global", "summary", "description"};
		String []requestSelectColumns = {"task_id", "description", "quantity", "mediatype"};
		ArrayList<Task> tasklist = new ArrayList<Task>();
		
		Cursor userTasks = db.query("Tasks", 
				taskSelectColumns, taskSelectColumns[0] + " = " + user, 
				null, null, null, null);
		
		for (userTasks.moveToFirst(); !userTasks.isAfterLast(); userTasks.moveToNext()) {
			Task newTask = new Task(UUID.fromString(userTasks.getString(2)),
					UUID.fromString(userTasks.getString(1)));
			
			newTask.setSummary(userTasks.getString(4));
			newTask.setDescription(userTasks.getString(5));
			
			if (userTasks.getInt(3) == 1) {
				newTask.isGlobal();
			}
			else {
				newTask.isLocal();
			}
			
			// GET ALL REQUESTS ASSOCIATED WITH THE TASK
			Cursor taskRequests = db.query("Requests", requestSelectColumns, 
					requestSelectColumns[0] + " = " + userTasks.getString(2), 
					null, null, null, null);
			
			for (taskRequests.moveToFirst(); !taskRequests.isAfterLast(); taskRequests.moveToNext()) {
				Request newRequest = new Request(MediaType.valueOf(taskRequests.getString(4)));
				newRequest.setDescription(taskRequests.getString(2));
				newRequest.setQuantity(taskRequests.getInt(3));
				newTask.addRequest(newRequest);
			}
			
			tasklist.add(newTask);
		}
		
		
		return tasklist;
	}
	
	public static ArrayList<Task> getTasks(SQLiteDatabase db, boolean global) {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		String []taskSelectColumns = {"userid", "id", "global", "summary", "description"};
		String []requestSelectColumns = {"task_id", "description", "quantity", "mediatype"};
		
		Cursor scopedTasks;
		
		if (global) {
			scopedTasks = db.query("Tasks", 
					taskSelectColumns, taskSelectColumns[2] + " = " + 1, 
					null, null, null, null);
		} else {
			scopedTasks = db.query("Tasks", 
					taskSelectColumns, taskSelectColumns[2] + " = " + 0, 
					null, null, null, null);
		}
		
		for (scopedTasks.moveToFirst(); !scopedTasks.isAfterLast(); scopedTasks.moveToNext()) {
			Task newTask = new Task(UUID.fromString(scopedTasks.getString(2)),
					UUID.fromString(scopedTasks.getString(1)));
			
			newTask.setSummary(scopedTasks.getString(4));
			newTask.setDescription(scopedTasks.getString(5));
			if (global) {
				newTask.isGlobal();
			} else {
				newTask.isLocal();
			}
			
			// GET ALL REQUESTS ASSOCIATED WITH THE TASK
			Cursor taskRequests = db.query("Requests", requestSelectColumns, 
					requestSelectColumns[0] + " = " + scopedTasks.getString(2), 
					null, null, null, null);

			for (taskRequests.moveToFirst(); !taskRequests.isAfterLast(); taskRequests.moveToNext()) {
				Request newRequest = new Request(MediaType.valueOf(taskRequests.getString(4)));
				newRequest.setDescription(taskRequests.getString(2));
				newRequest.setQuantity(taskRequests.getInt(3));
				newTask.addRequest(newRequest);
			}

			tasklist.add(newTask);
		}
		
		return tasklist;
	}
	
}
