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
import android.telephony.TelephonyManager;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.localStorage.DeviceStorage;
import ca.ualberta.cs.c301f12t01.model.ReportManager;
import ca.ualberta.cs.c301f12t01.model.StorageInterface;
import ca.ualberta.cs.c301f12t01.model.TaskCollection;
import ca.ualberta.cs.c301f12t01.model.TaskManager;

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

    private static TaskSourceApplication app = null;
    
    /*
     *  STATIC DELEGATE METHODS.
     */

    /**
     * Adds a task to the singleton TaskManager. This Task will find itself into
     * the appropriate TaskCollections.
     */
    public static void singletonAddTask(Task newTask) {
        app.addTask(newTask);
    }

	/** Gets this device's user ID as a string. */
    public static String getSingletonUserID() {
        return app.getUserID();
    }

    /** Gets the named TaskCollection from the singleton TaskManager. */
    public static TaskCollection getTaskCollectionByName(String name) {
        return app.getCollectionByName(name);
    }


    
    /*
     * Handles the singleton's instantiation.
     */

    /** Sets the static application to the singleton instance. */
    public TaskSourceApplication() {
        if (app == null) {
            app = this;
        }
    }

    /** On create, we set the instance and load everything. */
    @Override
    public void onCreate() {
      super.onCreate();  
      app = this;

      /* I LIED! Let's eagerly set everything up. */
      //setupReportManager();
      //setupTaskManager();
      setupUserId();
      
      /* I JUST KEEP ON LIEING! */
    }

    
    /*
     * Delegate instance methods.
     */

    /**
	 * @see ca.ualberta.cs.c301f12t01.model.TaskManager#getCollectionByName(java.lang.String)
	 */
	public TaskCollection getCollectionByName(String name) {
		setupTaskManager();
		return taskManager.getCollectionByName(name);
	}

	public void addTask(Task newTask) {
		taskManager.addTask(newTask);
	}

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.TaskManager#get(java.util.UUID)
	 */
	public Task get(UUID id) {
		setupTaskManager();
		return taskManager.get(id);
	}

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.TaskManager#getLocalTaskCollection()
	 */
	public TaskCollection getLocalTaskCollection() {
		setupTaskManager();
		return taskManager.getLocalTaskCollection();
	}

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.TaskManager#getGlobalTaskCollection()
	 */
	public TaskCollection getGlobalTaskCollection() {
		setupTaskManager();
		return taskManager.getGlobalTaskCollection();
	}

    /**
	 * @see ca.ualberta.cs.c301f12t01.model.ReportManager#getReports(ca.ualberta.cs.c301f12t01.common.Task)
	 */
	public List<Report> getReports(Task task) {
		setupReportManager();
		return reportManager.getReports(task);
	}

	/**
	 * @see ca.ualberta.cs.c301f12t01.model.ReportManager#taskHasReports(ca.ualberta.cs.c301f12t01.common.Task)
	 */
	public boolean taskHasReports(Task task) {
		setupReportManager();
		return reportManager.taskHasReports(task);
	}
	
	
	/*
	 * Methods that manage the instances that belong to an Application. 
	 */
	
    /** Returns the user ID as a string. */
    public String getUserID() {
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
		
	    DeviceStorage localStorage = new DeviceStorage(getApplicationContext());
	
	    /* TODO: Get the server interface working! */
	    // StorageInterface serverStorage = null;
	
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
	    // taskManager.getGlobalTaskCollection().addObserver(serverStorage);
	
	    return taskManager;
	}

	/** Sets up and returns the singleton ReportManager. */
    private ReportManager setupReportManager() {
    	if (reportManager != null) {
    		return reportManager;
    	}
    	
        StorageInterface localStorage = new DeviceStorage(
                getApplicationContext());
        /* TODO: Get the server interface working! */
        reportManager = ReportManager.getInstance();
        // Rmanager.addObserver(localStorage);
        reportManager.setLocalStorage(localStorage);

        return reportManager;

    }

}
