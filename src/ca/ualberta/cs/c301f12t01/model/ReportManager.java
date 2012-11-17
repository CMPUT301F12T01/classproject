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
package ca.ualberta.cs.c301f12t01.model;

import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Report;

/**
 * Class to manage all of our reports Singleton design pattern
 * 
 * @author home
 * 
 */
public class ReportManager extends Observable{

	// our instance
	private static final ReportManager instance = new ReportManager();
	// our storage
	private StorageInterface localStorage;

	/**
	 * 
	 * @param taskID
	 *            Task that you want to get reports for
	 * @return The Collection of reports associated with our task
	 */
	public List<Report> getReports(UUID taskID) {
		if(localStorage == null){
			System.out.println("local storage was null");
		}
		Collection<Report> c = localStorage.getLocalReports(taskID);
		List<Report> l = (List<Report>) c;
		return l;
		// return localStorage.getLocalReports(taskID);
	}

	/**
	 * 
	 * @param report
	 */
	public void addReport(Report newReport) {
		/**
		 * TODO make this handle global as well
		 */
		setChanged();
		notifyObservers(newReport);
		// localStorage.storeReport(report);
	}

	/**
	 * Setter
	 * 
	 * @param localStorage
	 *            StorageInterface to get reports from
	 */
	public void setLocalStorage(StorageInterface localStorage) {
		System.out.println("Set Local Storage");
		this.localStorage = localStorage;
	}

	/**
	 * let them get our instance
	 * 
	 * @return Singleton instance of TaskManager
	 */
	public static ReportManager getInstance() {
		return instance;
	}

	/**
	 * private constructor
	 */
	private ReportManager() {
		// nothing to do really
	}

}
