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

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import ca.ualberta.cs.c301f12t01.localStorage.DeviceStorage;
import ca.ualberta.cs.c301f12t01.model.ReportManager;
import ca.ualberta.cs.c301f12t01.model.StorageInterface;
import ca.ualberta.cs.c301f12t01.model.TaskManager;

/**
 * Maintains global singleton variables.
 * 
 * Many of these are lazily loaded.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public class TaskSourceApplication extends Application {

	private TaskManager taskManager = null;
	private ReportManager reportManager = null;
	/* TODO MAKE THIS LESS UGLY PLEASE */
	private String user = null;

	/** Returns the user ID as a string. */
    public String getUserID() {
        if (user == null) {
            user = fetchAndroidDeviceId();
        }
        
        return user;
    }

    /** Returns the Task Manager. */
	public TaskManager getTaskManager() {
		/* Lazily loads the manager. */
		if (taskManager == null) {
			setupTaskManager();
		}

		return taskManager;
	}
	
	/** Returns the Report Manager */
	public ReportManager getReportManager() {
		/* Lazily loads the manager. */
		if (reportManager == null) {
			setupReportManager();
		}

		return reportManager;
	}

	private ReportManager setupReportManager() {
		StorageInterface localStorage = new DeviceStorage(getApplicationContext());
		/* TODO: Get the server interface working! */
		reportManager = ReportManager.getInstance();
		//Rmanager.addObserver(localStorage);
		reportManager.setLocalStorage(localStorage);

		return reportManager;

	}

	private TaskManager setupTaskManager() {
		DeviceStorage localStorage = new DeviceStorage(getApplicationContext());
		/* TODO: Get the server interface working! */
		// StorageInterface serverStorage = null;

		taskManager = TaskManager.getInstance();
		taskManager.getLocalTaskCollection().addObserver(localStorage);

		return taskManager;
	}
	
	/* Gets the device ID. */
	private String fetchAndroidDeviceId() {
	    TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	    String uid = tManager.getDeviceId();
	    
	    return uid;
	}

}
