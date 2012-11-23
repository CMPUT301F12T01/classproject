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

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * This class is responsible for updating or
 * removing a Task that already exists in the
 * database
 * 
 * @author Neil
 *
 */
public class TaskLocalModify {
	
	/**
	 * Updates a Task in the Database
	 * 
	 * @param Database db
	 * @param Task taskToUpdate
	 */
	public static void updateTask(SQLiteDatabase db, Task taskToUpdate) {
		
		String taskTable = "Tasks";
		String requestTable = "Requests";
		String []taskCollumns = {"userid", "id", "global", 
				"summary", "description"};
		String []reqCollumns = {"task_id", "description",
				"quantity", "amountfulfilled", "mediatype"};

		// Get all the fields from the task 
		String userId = taskToUpdate.getUser().toString();
		String id = taskToUpdate.getId().toString();
		int global = 0;
		String summary = taskToUpdate.getSummary();
		String description = taskToUpdate.getDescription();

		if (taskToUpdate.isGlobal()) {
			global = 1;
		}

		// store all the extracted fields into the database
		ContentValues taskValues = new ContentValues();
		taskValues.put(taskCollumns[0], userId);
		taskValues.put(taskCollumns[2], global);
		taskValues.put(taskCollumns[3], summary);
		taskValues.put(taskCollumns[4], description);

		//TODO ERROR HANDLING IF db.insert returns -1
		db.update(taskTable, taskValues, taskCollumns[1] + " = '" + id
		+ "'", null);
		
		deleteRequests(db, taskToUpdate);

		for (Request request : taskToUpdate) {

			ContentValues requestValues = new ContentValues();
			requestValues.put(reqCollumns[0], taskToUpdate.getId().toString());
			requestValues.put(reqCollumns[1], request.getDescription());
			requestValues.put(reqCollumns[2], request.getQuantity());
			requestValues.put(reqCollumns[3], 0);
			requestValues.put(reqCollumns[4], request.getType().toString());
			db.insert(requestTable, null, requestValues);

		}

	}

	/**
	 * Removes a Task entry from the database
	 * 
	 * @param Database db
	 * @param Task taskToRemove
	 */
	public static void removeTask(SQLiteDatabase db, Task taskToRemove) {

		String taskTable = "Tasks";
		String id = taskToRemove.getId().toString();
		
		//TODO ERROR HANDLING IF db.insert returns -1
		db.delete(taskTable, "id = '" + id
				+ "'", null);
		
		deleteRequests(db, taskToRemove);

	}
	
	private static void deleteRequests(SQLiteDatabase db, Task taskToRemove) {

		String requestTable = "Requests";

		// Get the task it
		String id = taskToRemove.getId().toString();
		
		db.delete(requestTable, "task_id = '" + id
				+ "'", null);
	}
}
