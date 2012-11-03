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
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Response;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.StorageInterface;

/**
 * Breaking up concerns needs to be done, plus this header
 * 
 * @author Neil Borle
 */

public class DeviceStorage implements StorageInterface, Observer {
	/*
	 * This class is responsible for creating 
	 * a connection to a database in Android
	 * and providing the functionality to 
	 * write and retrieve objects from it
	 */ 
	
	private DBInstance instance;
	private SQLiteDatabase database;
	
	public DeviceStorage(Context context) {
		instance = new DBInstance(context);
	}
	
	public void open() throws SQLException {
		database = instance.getWritableDatabase();
	}

	
	public void close() {
		// close the database connection
		if (database != null)
			database.close();
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#storeTask(ca.ualberta.cs.c301f12t01.common.Task)
	 */
	public void storeTask(Task taskToStore) {
		// Delegate task storage to taskLocalStorage class
		//taskLocalStorage.storeTask(database, taskToStore);
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#getOwnTasks(java.util.UUID)
	 */
	public ArrayList<Task> getOwnTasks(UUID userid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#getLocalTasks()
	 */
	public ArrayList<Task> getLocalTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#getGlobalTasks()
	 */
	public ArrayList<Task> getGlobalTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#storeReport(ca.ualberta.cs.c301f12t01.common.Report)
	 */
	public void storeReport(Report reportToStore) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#getLocalReports(java.util.UUID)
	 */
	public ArrayList<Report> getLocalReports(UUID taskid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#getGlobalReports(java.util.UUID)
	 */
	public ArrayList<Report> getGlobalReports(UUID taskid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#storeRequest(ca.ualberta.cs.c301f12t01.common.Request)
	 */
	public void storeRequest(Request requestToStore) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#getRequests(java.util.UUID)
	 */
	public ArrayList<Request> getRequests(UUID taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#storeResponse(ca.ualberta.cs.c301f12t01.common.Response)
	 */
	public void storeResponse(Response responseToStore) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.model.StorageInterface#getResponses(java.util.UUID)
	 */
	public ArrayList<Response> getResponses(UUID reportId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
