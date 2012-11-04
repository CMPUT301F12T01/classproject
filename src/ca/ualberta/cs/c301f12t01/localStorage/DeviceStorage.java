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

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Sharing;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.StorageInterface;

/**
 * Breaking up concerns needs to be done, plus this header
 * 
 * @author Neil Borle
 */

public class DeviceStorage implements StorageInterface, Observer {
	/**
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

	/**
	 * Notify everyone!
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * The following methods deal with the storage and 
	 * retrieval of Tasks from the database. They delegate
	 * the hard work off to the taskLocalStorage class
	 */
	public void storeTask(Task taskToStore) {
		// Delegate task storage to taskLocalStorage class
		TaskLocalStorage.storeTask(database, taskToStore);
	}

	public Collection<Task> getOwnTasks(UUID userid) {
		// Delegate task storage to taskLocalStorage class
		return TaskLocalStorage.getOwnTasks(database, userid);
	}

	public Collection<Task> getLocalTasks() {
		// Delegate task retrieval to taskLocalStorage class
		return TaskLocalStorage.getTasks(database, false);
	}

	public Collection<Task> getGlobalTasks() {
		// Delegate task retrieval to taskLocalStorage class
		return TaskLocalStorage.getTasks(database, true);
	}

	/**
	 * The following methods deal with the storage and 
	 * retrieval of Reports from the database.
	 * The hard work has been delegated off to the
	 * reportLocalStorage class
	 */
	public void storeReport(Report reportToStore) {
		// Delegate Report storage to ReportLocalStorage
		ReportLocalStorage.storeReport(database, reportToStore);
	}

	public Collection<Report> getLocalReports(UUID taskid) {
		// Delegate Report retrieval to ReportLocalStorage class
		return ReportLocalStorage.getReports(database, taskid, Sharing.LOCAL);
	}
	
	public Collection<Report> getTaskCreatorReports(UUID taskid) {
		// Delegate Report retrieval to ReportLocalStorage class
		return ReportLocalStorage.getReports(database, taskid, Sharing.TASK_CREATOR);
	}

	public Collection<Report> getGlobalReports(UUID taskid) {
		// Delegate Report retrieval to ReportLocalStorage class
		return ReportLocalStorage.getReports(database, taskid, Sharing.GLOBAL);
	}
	
}
