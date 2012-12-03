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
import java.util.HashMap;
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
import ca.ualberta.cs.c301f12t01.util.Message;

/**
 * Obtaining an instance of this class allows for the storing
 * and retrieving of Tasks, Reports, Requests and Responses
 * from the SQLDatabase. This class is responsible for creating 
 * a connection to a database in Android and providing the 
 * functionality to write and retrieve objects from it
 * 
 * Refactored to deal with HashMaps and to pass Task object
 * instead of UUIDs to get reports
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
	 * The database can be notified when a Task or Report
	 * has been remove/added/updated so that it can update
	 * itself as needed.
	 * 
	 * @param Observable, Object message
	 */
	public void update(Observable obsv, Object message) {
		
		Message newMessage = (Message) message;
		
		if (newMessage.getPayload() instanceof Task) {
			
			Task newTask = (Task) newMessage.getPayload();
			
			switch (newMessage.getAction()) {
				case ADDED:
					storeTask(newTask);
					break;
				case REMOVED:
					removeTask(newTask);
					break;
				case MODIFIED:
					updateTask(newTask);
					break;
			}
		} 
		else if (newMessage.getPayload() instanceof Report) {
			
			Report newReport = (Report) newMessage.getPayload();
			
			switch (newMessage.getAction()) {
			case ADDED:
				storeReport(newReport);
				break;
			case REMOVED:
				removeReport(newReport);
				break;
			case MODIFIED:
				updateReport(newReport);
				break;
			}
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
	 * Update a Task in the database
	 * 
	 * @param Task
	 */
	public void updateTask(Task taskToUpdate) {
		TaskLocalModify.updateTask(database, taskToUpdate);
	}
	
	/**
	 * Remove a task from the database
	 * 
	 * @param Task
	 */
	public void removeTask(Task taskToRemove) {
		TaskLocalModify.removeTask(database, taskToRemove);
	}

	/**
	 * Find all the Tasks that belong to a specific user
	 * 
	 * @param UUID
	 */
	public HashMap<UUID, Task> getOwnTasks(String userid) {
		// Delegate task storage to taskLocalStorage class
		HashMap<UUID, Task> taskHash;
		taskHash = TaskLocalRetrieval.getTasks(database, userid, false);
		
		return taskHash;
	}

	/**
	 * Get all tasks with local scope
	 * 
	 * @param
	 */
	public HashMap<UUID, Task> getLocalTasks() {
		// Delegate task retrieval to taskLocalStorage class
		HashMap<UUID, Task> taskHash;
		taskHash = TaskLocalRetrieval.getTasks(database, null, false);
		
		return taskHash;
	}

	/**
	 * Get all tasks with global scope
	 * 
	 * @param
	 */
	public HashMap<UUID, Task> getGlobalTasks() {
		// Delegate task retrieval to taskLocalStorage class
		HashMap<UUID, Task> taskHash;
		taskHash = TaskLocalRetrieval.getTasks(database, null, true);
		
		return taskHash;
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
	 * Update a report in the database
	 * 
	 * @param Report
	 */
	public void updateReport(Report reportToUpdate) {
		ReportLocalModify.updateReport(database, reportToUpdate);
	}
	
	/**
	 * remove a report from the database
	 * 
	 * @param Report
	 */
	public void removeReport(Report reportToRemove) {
		ReportLocalModify.removeReport(database, reportToRemove);
	}
	
	/**
	 * Get all local Reports associated with a specific Task
	 * 
	 * @param Task
	 */
	public ArrayList<Report> getAllReports() {
		// Delegate Report retrieval to ReportLocalStorage class
		ArrayList<Report> reportList;
		reportList = ReportLocalRetrieval.getReports(database, null, null);
		
		return reportList;
	}

	/**
	 * Get all local Reports associated with a specific Task
	 * 
	 * @param Task
	 */
	public ArrayList<Report> getReports(Task matchingTask) {
		// Delegate Report retrieval to ReportLocalStorage class
		ArrayList<Report> reportList;
		reportList = ReportLocalRetrieval.getReports(database, matchingTask, null);
		
		return reportList;
	}
	
	/**
	 * Get all local Reports associated with a specific Task
	 * 
	 * @param Task
	 */
	public ArrayList<Report> getLocalReports(Task matchingTask) {
		// Delegate Report retrieval to ReportLocalStorage class
		ArrayList<Report> reportList;
		reportList = ReportLocalRetrieval.getReports(database, matchingTask, Sharing.LOCAL);
		
		return reportList;
	}
	
	/**
	 * Get all Reports for a task where only the task creator
	 * and the task fulfiller can see that Report
	 * 
	 * @param Task
	 */
	public ArrayList<Report> getTaskCreatorReports(Task matchingTask) {
		// Delegate Report retrieval to ReportLocalStorage class
		ArrayList<Report> reportList;
		reportList = ReportLocalRetrieval.getReports(database, matchingTask, Sharing.TASK_CREATOR);
		
		return reportList;
	}

	/**
	 * Get all the Reports with Global scope that are associated
	 * with a particular Task
	 * 
	 * @param Task
	 */
	public ArrayList<Report> getGlobalReports(Task matchingTask) {
		// Delegate Report retrieval to ReportLocalStorage class
		ArrayList<Report> reportList;
		reportList = ReportLocalRetrieval.getReports(database, matchingTask, Sharing.GLOBAL);
		
		return reportList;
	}
	
}
