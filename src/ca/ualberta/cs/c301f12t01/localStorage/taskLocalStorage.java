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
import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * This class abstracts out the storage 
 * and retrieval of Tasks from Device storage
 * in the interest of flexible code
 * 
 * @author Neil Borle
 *
 */
public class taskLocalStorage {

	public static void storeTask(SQLiteDatabase db, Task taskToStore) {
		
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
		ContentValues values = new ContentValues();
	    values.put("userid", userId);
	    values.put("id", id);
	    values.put("global", global);
	    values.put("summary", summary);
	    values.put("description", description);
	    long rowId = db.insert("Tasks", null, values);
	    System.out.println(rowId);
	}
	
	public static ArrayList<Task> getOwnTasks(SQLiteDatabase db, UUID userid) {
		String user = userid.toString();
		String []selectColumns = {"userid", "id", "global", "summary", "description"};
		ArrayList<Task> tasklist = new ArrayList<Task>();
		
		Cursor cursor = db.query("Tasks", 
				selectColumns, selectColumns[0] + " = " + user, 
				null, null, null, null);
		
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Task newTask = new Task(UUID.fromString(cursor.getString(2)),
					UUID.fromString(cursor.getString(1)));
			
			newTask.setSummary(cursor.getString(4));
			newTask.setDescription(cursor.getString(5));
			
			if (cursor.getInt(3) == 1) {
				newTask.isGlobal();
			}
			else {
				newTask.isLocal();
			}
			
			tasklist.add(newTask);
		}
		
		
		return tasklist;
	}
	
	public static ArrayList<Task> getTasks(SQLiteDatabase db, boolean global) {
		String []selectColumns = {"userid", "id", "global", "summary", "description"};
		ArrayList<Task> tasklist = new ArrayList<Task>();
		
		Cursor cursor;
		
		if (global) {
			cursor = db.query("Tasks", 
					selectColumns, selectColumns[2] + " = " + 1, 
					null, null, null, null);
		} else {
			cursor = db.query("Tasks", 
					selectColumns, selectColumns[2] + " = " + 0, 
					null, null, null, null);
		}
		
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Task newTask = new Task(UUID.fromString(cursor.getString(2)),
					UUID.fromString(cursor.getString(1)));
			
			newTask.setSummary(cursor.getString(4));
			newTask.setDescription(cursor.getString(5));
			if (global) {
				newTask.isGlobal();
			} else {
				newTask.isLocal();
			}
			
			tasklist.add(newTask);
		}
		
		return tasklist;
	}
	
}
