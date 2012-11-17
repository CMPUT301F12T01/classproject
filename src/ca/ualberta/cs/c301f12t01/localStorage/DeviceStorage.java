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
import ca.ualberta.cs.c301f12t01.common.Sharing;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.StorageInterface;

/**
 * Obtaining an instance of this class allows for the storing
 * and retrieving of Tasks, Reports, Requests and Responses
 * from the SQLDatabase. This class is responsible for creating 
 * a connection to a database in Android and providing the 
 * functionality to write and retrieve objects from it
 * 
 * @author Neil Borle
 */
public class DeviceStorage implements StorageInterface, Observer {
	
	private DBInstance instance;
	private SQLiteDatabase database;
	
	// TODO HANDLE CLOSING THE DATABASE WITHOUT NULLPOINTEREXECPTIONS
	/**
	 * Obtain a new instance of the Device Storage and open()
	 * to get a SQLiteDatabase
	 * 
	 * @param context
	 */
	public DeviceStorage(Context context) {
		instance = new DBInstance(context);
		open();
	}
	
	public void open() throws SQLException {
		database = instance.getWritableDatabase();
	}

	/**
	 * Close the database connection
	 */
	public void close() {
		// close the database connection
		if (database != null)
			database.close();
	}

	/**
	 * Notify everyone! //That isnt what this method is for . . .
	 * This method written by Mitchell Home
	 * Should eliminate need for TaskObserver and ReportObserver
	 */
	public void update(Observable obsv, Object arg) {
		if (arg instanceof Report){
			Report newReport = (Report) arg;
			storeReport(newReport);
		}
		if (arg instanceof Task){
			Task newTask = (Task) arg;
			storeTask(newTask);
		}
	}

	/*
	 * The following methods deal with the storage and 
	 * retrieval of Tasks from the database. They delegate
	 * the hard work off to the taskLocalStorage class
	 */
	
	/**
	 * Store a task to the database
	 * 
	 * @param Task
	 */
	public void storeTask(Task taskToStore) {
		// Delegate task storage to taskLocalStorage class
		TaskLocalStorage.storeTask(database, taskToStore);
	}

	/**
	 * Find all the Tasks that belong to a specific user
	 * 
	 * @param UUID
	 */
	public ArrayList<Task> getOwnTasks(UUID userid) {
		// Delegate task storage to taskLocalStorage class
		ArrayList<Task> taskList;
		taskList = TaskLocalStorage.getOwnTasks(database, userid);
		
		return taskList;
	}

	/**
	 * Get all tasks with local scope
	 * 
	 * @param
	 */
	public ArrayList<Task> getLocalTasks() {
		// Delegate task retrieval to taskLocalStorage class
		ArrayList<Task> taskList;
		taskList = TaskLocalStorage.getTasks(database, false);
		
		return taskList;
	}

	/**
	 * Get all tasks with global scope
	 * 
	 * @param
	 */
	public ArrayList<Task> getGlobalTasks() {
		// Delegate task retrieval to taskLocalStorage class
		ArrayList<Task> taskList;
		taskList = TaskLocalStorage.getTasks(database, true);
		
		return taskList;
	}

	/*
	 * The following methods deal with the storage and 
	 * retrieval of Reports from the database.
	 * The hard work has been delegated off to the
	 * reportLocalStorage class
	 */
	
	/**
	 * Store a Report to the database
	 * 
	 * @param Report
	 */
	public void storeReport(Report reportToStore) {
		// Delegate Report storage to ReportLocalStorage
		ReportLocalStorage.storeReport(database, reportToStore);
	}

	/**
	 * Get all local Reports associated with a specific Task
	 * 
	 * @param UUID
	 */
	public ArrayList<Report> getLocalReports(UUID taskid) {
		// Delegate Report retrieval to ReportLocalStorage class
		ArrayList<Report> reportList;
		reportList = ReportLocalStorage.getReports(database, taskid, Sharing.LOCAL);
		
		return reportList;
	}
	
	/**
	 * Get all Reports for a task where only the task creator
	 * and the task fulfiller can see that Report
	 * 
	 * @param UUID
	 */
	public ArrayList<Report> getTaskCreatorReports(UUID taskid) {
		// Delegate Report retrieval to ReportLocalStorage class
		ArrayList<Report> reportList;
		reportList = ReportLocalStorage.getReports(database, taskid, Sharing.TASK_CREATOR);
		
		return reportList;
	}

	/**
	 * Get all the Reports with Global scope that are associated
	 * with a particular Task
	 * 
	 * @param UUID
	 */
	public ArrayList<Report> getGlobalReports(UUID taskid) {
		// Delegate Report retrieval to ReportLocalStorage class
		ArrayList<Report> reportList;
		reportList = ReportLocalStorage.getReports(database, taskid, Sharing.GLOBAL);
		
		return reportList;
	}
	
}
