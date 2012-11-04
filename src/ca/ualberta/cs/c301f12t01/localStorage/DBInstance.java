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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class will instantiate a database
 * with four tables, one for tasks, reports
 * requests and responses.
 * 
 * @author Neil Borle
 */

public class DBInstance extends SQLiteOpenHelper{
	/**
	 * This class is a basic implementation of a SQLlite
	 * helper to create a database
	 * 
	 * Resources used to construct this code
	 * http://www.vogella.com/articles/AndroidSQLite/article.html
	 */
	
	private static final String DATABASE_NAME = "TaskSource.db";
	
	private static final String DATABASE_CREATE = "CREATE TABLE Tasks "
		      + "(userid TEXT NOT NULL, " //have to store UUIDs as Text
			  + "id TEXT PRIMARY KEY, " //have to store UUIDs as Text
		      + "global INTEGER, " // 0 is local and 1 is global
		      + "summary TEXT, "
		      + "description TEXT); "
		      
		      + "CREATE TABLE Requests "
		      + "(description TEXT, "
		      + "mediatype TEXT, "
			  + "quantity INTEGER, "
			  + "amountfulfilled INTEGER, "
			  + "task_id TEXT NOT NULL, " //have to store UUIDs as Text
		      + "foreign key(task_id) references Tasks(id) NOT NULL); "
		      
		      + "CREATE TABLE Reports "
		      + "(scope TEXT, "
		      + "timestamp TEXT NOT NULL, "
		      + "id TEXT PRIMARY KEY, " //have to store UUIDs as Text
			  + "task_id TEXT NOT NULL, " //have to store UUIDs as Text
		      + "foreign key(task_id) references Tasks(id) NOT NULL);"
		      
		      + "CREATE TABLE Responses "
		      + "(mediatype TEXT, "
			  + "report_id TEXT, " //have to store UUIDs as Texts
		      + "foreign key(report_id) references Report(id) NOT NULL, " 
			  + "media BLOB);";
	
	public DBInstance(Context context) {
	    super(context, DATABASE_NAME, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		// This method is called IF the database does not exist
		database.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop all tables and create a new table
	    db.execSQL("DROP TABLE IF EXISTS Tasks");
	    db.execSQL("DROP TABLE IF EXISTS Reports");
	    db.execSQL("DROP TABLE IF EXISTS Request");
	    db.execSQL("DROP TABLE IF EXISTS Response");
	    onCreate(db);
	} 
}
