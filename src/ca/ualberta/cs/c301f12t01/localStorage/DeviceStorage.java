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

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import ca.ualberta.cs.c301f12t01.model.StorageInterface;

public class DeviceStorage implements StorageInterface, Observer {
	/*
	 * 
	 */ 
	
	private SQLiteDatabase database;
	private DBInstance instance;
	
	public DeviceStorage(Context context) {
		instance = new DBInstance(context);
	}
	
	public void open() throws SQLException {
		database = instance.getWritableDatabase();
	}

	// close the database connection
	public void close() {
		if (database != null)
			database.close();
	}
	
	public void storeTask() {
		
	}
	
	public void getLocalTasks() {
		
	}
	
	public void getGlobalTasks() {
		
	}

	public void storeReport() {
		
	}
	
	public void getLocalReports() {
		
	}
	
	public void getGlobalReports() {
		
	}

	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}

}
