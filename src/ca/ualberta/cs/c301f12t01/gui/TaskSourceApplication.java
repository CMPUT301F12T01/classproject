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
package ca.ualberta.cs.c301f12t01.gui;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.localStorage.DeviceStorage;
import ca.ualberta.cs.c301f12t01.model.ReportManager;
import ca.ualberta.cs.c301f12t01.model.TaskCollection;
import ca.ualberta.cs.c301f12t01.model.TaskManager;
import ca.ualberta.cs.c301f12t01.serverStorage.ServerStorage;

/**
 * Singleton app class that manages all global services.
 * 
 * Many of the instances managed here are lazily loaded.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public class TaskSourceApplication extends Application {

	private TaskManager taskManager = null;
	private ReportManager reportManager = null;
	private String user = null;
	private DeviceStorage localStorage;

	private ServerStorage serverStorage;

	private static TaskSourceApplication app = null;

	/*
	 * STATIC DELEGATE METHODS.
	 * 
	 * USE THESE IN YOUR CODE.
	 */

	/** Instantiates a new Task with the current user ID. */
	public static Task newTaskForCurrentUser() {
		return new Task(getUserID());
	}

	/** Gets this device's user ID as a string. */
	public static String getUserID() {
		return app.getUserIDFromInstance();
	}

	/**
	 * Adds a task to the singleton TaskManager. This Task will find itself into
	 * the appropriate TaskCollections.
	 */
	public static void addTask(Task newTask) {
		app.addTaskFromInstance(newTask);
	}

	/** Modifies the given task. */
	public static void modifyTask(Task oldTask, Task newTask) {
		app.modifyTaskFromInstance(oldTask, newTask);
	}

	/** Gets the task with the give task ID from the TaskManager. */
	public static Task getTask(UUID taskId) {
		return app.getTaskFromInstance(taskId);
	}

	/** Removes the task, from where ever it may exist. */
	public static void removeTask(Task task) {
		app.removeTaskFromInstance(task);
	}

	/** Gets the named TaskCollection from the singleton TaskManager. */
	public static TaskCollection getTaskCollectionByName(String name) {
		return app.getCollectionByNameFromInstance(name);
	}

	/** Gets the TaskCollection that maintains local tasks. */
	public static TaskCollection getLocalTaskCollection() {
		return app.getLocalTaskCollectionFromInstance();
	}

	/** Gets the TaskCollection that maintains local tasks. */
	public static TaskCollection getGlobalTaskCollection() {
		return app.getGlobalTaskCollectionFromInstance();
	}

	/** Adds a report to be tracked by the singleton ReportManager instance. */
	public static void addReport(Report newReport) {
		app.addReportFromInstance(newReport);
	}

	/** Gets reports for the given Task from the singleton ReportManager. */
	public static Report getReport(UUID reportId) {
		return app.getReportFromInstance(reportId);
	}

	/** Gets reports for the given Task from the singleton ReportManager. */
	public static List<Report> getReportsForTask(UUID taskId) {
		return app.getReportsForTaskFromInstance(taskId);
	}

	/**
	 * Returns true if the given Task has at least one Report tracked in the
	 * singleton ReportManager.
	 */
	public static boolean taskHasReports(UUID taskId) {
		return app.taskHasReportsFromInstance(taskId);
	}

	/*
	 * Handles the singleton's instantiation.
	 */

	/**
	 * Returns the singleton TaskSourceApplication instance.
	 * 
	 * @return
	 */
	public static TaskSourceApplication getInstance() {
		return app;
	}

	/** Sets the static application to the singleton instance. */
	public TaskSourceApplication() {
	}

	/** On create, we set the instance and load everything. */
	@Override
	public void onCreate() {
		super.onCreate();
		app = this;

		localStorage = new DeviceStorage(getApplicationContext());
		serverStorage = new ServerStorage();

		/* I LIED! Let's eagerly set everything up. */
		// setupReportManager();
		// setupTaskManager();
		setupUserId();

		/* I JUST KEEP ON LIEING! */
	}

	/*
	 * Delegate instance methods.
	 */

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.TaskManager#getCollectionByName(java.lang.String)
	 */
	public TaskCollection getCollectionByNameFromInstance(String name) {
		setupTaskManager();
		return taskManager.getCollectionByName(name);
	}

	public void addTaskFromInstance(Task newTask) {
		setupTaskManager();
		taskManager.addTask(newTask);
	}

	public void modifyTaskFromInstance(Task oldTask, Task newTask) {
		setupTaskManager();
		taskManager.modifyTask(oldTask, newTask);
	}

	public void removeTaskFromInstance(Task task) {
		setupTaskManager();
		taskManager.removeTask(task);
	}

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.TaskManager#get(java.util.UUID)
	 */
	public Task getTaskFromInstance(UUID id) {
		setupTaskManager();
		return taskManager.get(id);
	}

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.TaskManager#getLocalTaskCollection()
	 */
	public TaskCollection getLocalTaskCollectionFromInstance() {
		setupTaskManager();
		return taskManager.getLocalTaskCollection();
	}

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.TaskManager#getGlobalTaskCollection()
	 */
	public TaskCollection getGlobalTaskCollectionFromInstance() {
		setupTaskManager();
		return taskManager.getGlobalTaskCollection();
	}

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.ReportManager#addReport(ca.ualberta.cs.c301f12t01.common.Task)
	 */
	public void addReportFromInstance(Report newReport) {
		setupReportManager();
		reportManager.add(newReport);
	}

	/**
	 * @param reportId
	 * @return
	 */
	public Report getReportFromInstance(UUID reportId) {
		setupReportManager();
		return reportManager.get(reportId);
	}

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.ReportManager#getReports(ca.ualberta.cs.c301f12t01.common.Task)
	 */
	public List<Report> getReportsForTaskFromInstance(UUID taskId) {
		setupReportManager();
		return reportManager.getAllReportsForTask(taskId);
	}

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.ReportManager#taskHasReports(ca.ualberta.cs.c301f12t01.common.Task)
	 */
	public boolean taskHasReportsFromInstance(UUID taskId) {
		setupReportManager();
		return reportManager.taskHasReports(taskId);
	}

	/*
	 * Methods that manage the instances that belong to an Application.
	 */

	/** Returns the user ID as a string. */
	public String getUserIDFromInstance() {
		setupUserId();
		return user;
	}

	/** Returns the Task Manager. */
	public TaskManager getTaskManager() {
		/* Lazily loads the manager. */
		setupTaskManager();
		return taskManager;
	}

	/** Returns the Report Manager */
	public ReportManager getReportManager() {
		/* Lazily loads the manager. */
		setupReportManager();
		return reportManager;
	}

	/*
	 * Methods that setup the global instances.
	 */

	/** Gets the device ID. */
	private String setupUserId() {
		if (user != null) {
			return user;
		}

		TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		user = tManager.getDeviceId();

		return user;
	}

	/** Sets up the singleton TaskManager. */
	private TaskManager setupTaskManager() {
		if (taskManager != null) {
			return taskManager;
		}

		/* Initialize TaskManager with stuff loaded from local storage. */
		Collection<Task> localTaskList = localStorage.getLocalTasks().values();
		Collection<Task> globalTaskList = localStorage.getGlobalTasks()
				.values();

		taskManager = TaskManager.initializeTaskMananger(localTaskList,
				globalTaskList);

		/*
		 * Make sure the storage thingymobobbers are observing the
		 * TaskCollections.
		 */
		taskManager.getLocalTaskCollection().addObserver(localStorage);
		taskManager.getGlobalTaskCollection().addObserver(localStorage);
		taskManager.getGlobalTaskCollection().addObserver(serverStorage);

		return taskManager;
	}

	/** Sets up and returns the singleton ReportManager. */
	private ReportManager setupReportManager() {
		if (reportManager != null) {
			return reportManager;
		}

		/* Initialize the ReportManager. */
		reportManager = new ReportManager(localStorage.getAllReports());

		
		fetchOnlineTasks();
		fetchOnlineReports();

		reportManager.addObserver(localStorage);
		reportManager.addObserver(serverStorage);

		return reportManager;

	}
	
	/*
	 * Online internet buisness stuff.
	 */

	
	private void fetchOnlineTasks() {
		new AsyncTask<Void, Void, Collection<Task>>() {

			@Override
			protected Collection<Task> doInBackground(Void... params) {
				return serverStorage.getGlobalTasks().values();
			}

			@Override
			protected void onPostExecute(Collection<Task> tasks) {
				for (Task task : tasks) {
					UUID taskID = task.getId();
					if (getTask(taskID) == null) {
						addTask(task);
					}
				}
			}

		}.execute();
		
	}

	
	private void fetchOnlineReports() {
		new AsyncTask<Void, Void, List<Report>>() {

			@Override
			protected List<Report> doInBackground(Void... params) {
				return serverStorage.getAllReports();
			}

			@Override
			protected void onPostExecute(List<Report> reports) {
				for (Report report : reports) {
					UUID reportID = report.getId();
					if (getReport(reportID) == null) {
						addReport(report);
					}
				}
			}

		}.execute();	
	}
	 

}
