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
 * This class abstracts out the storage 
 * Tasks from Device storage
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
}
