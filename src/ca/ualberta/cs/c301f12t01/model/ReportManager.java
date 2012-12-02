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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.util.ObservableCollection;

/**
 * Class to manage all of our reports.
 * 
 * This class is severely broken.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * @author home
 * 
 */
public class ReportManager extends ObservableCollection<UUID, Report> {

	/*
	 * This HashMap actually stores our reports, and allows us to access reports
	 * by ID.
	 */
	private HashMap<UUID, Report> reports = new HashMap<UUID, Report>();
	/* This is a secondary index that maps task IDs to report collections. */
	private HashMap<UUID, Collection<UUID>> taskIdToReportId = new HashMap<UUID, Collection<UUID>>();
	
	
	/**
	 * Creates a new ReportManager
	 */
	public ReportManager(Collection<Report> initialReports) {
		// nothing to do really

		/* Add all of the reports to these indices. */
		for (Report report : initialReports) {
			addNoNotify(report);
		}
	}

	/**
	 * Given a Task ID, returns ALL of the reports associated with it.
	 * 
	 * NOTE: this shouldn't be used very often, because I think it's very
	 * inefficient.
	 * 
	 * @param task
	 *            Task that you want to get reports for
	 * @return The Collection of reports associated with our task
	 */
	public List<Report> getAllReportsForTask(UUID taskID) {
		Collection<UUID> reportIDs = taskIdToReportId.get(taskID);
		int amountOfReports = reportIDs.size();

		ArrayList<Report> reportsForTask = new ArrayList<Report>(
				amountOfReports);
		for (UUID reportID : reportIDs) {
			reportsForTask.add(getReport(reportID));
		}

		return reportsForTask;
	}

	/**
	 * Gets the report with the given Report ID.
	 * 
	 * @param reportID
	 * @return the report, or null if not found.
	 */
	private Report getReport(UUID reportID) {
		return reports.get(reportID);
	}

	/** Returns true if the given task has reports. */
	public boolean taskHasReports(UUID taskId) {
		/*
		 * The Task ID -> Report ID index should only contain the key if and only if
		 * the Task has reports. Just in case, check if the size is greater than
		 * zero. Better not make any assumptions that could break in the future.
		 */
		return taskIdToReportId.containsKey(taskId)
				&& (taskIdToReportId.get(taskId).size() > 0);
	}



	/* I ONLY CARE ABOUT THE FOLLOWING FEW METHODS. */

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.ualberta.cs.c301f12t01.util.ObservableCollection#addNoNotify(java.lang.Object)
	 */
	@Override
	protected boolean addNoNotify(Report newReport) {
		/* Should add the Report to the global index and add it to the tasks's index. */
		
		UUID newReportID = newReport.getId();
		UUID taskId = newReport.getTaskID();
		
		/* Adds it the ALL reports. */
		reports.put(newReportID, newReport);
		
		/* Associate it with its task. */
		if (!taskHasReports(taskId)) {
			taskIdToReportId.put(taskId, new ArrayList<UUID>(1));
		}
		taskIdToReportId.get(taskId).add(newReportID);
		
		/* Pretend all of that above stuff worked. */
		return true;
	}

	@Override
	public Report get(UUID key) {
		return reports.get(key);
	}

	public Iterator<Report> iterator() {
		return reports.values().iterator();
	}

	public boolean contains(Object object) {
		return reports.containsValue(object);
	}
	
	public boolean isEmpty() {
		return reports.isEmpty();
	}
	
	public int size() {
		return reports.size();
	}
	
	
	
	
	/* I DON'T CARE ABOUT THE FOLLOWING METHODS RIGHT NOW. */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#clear()
	 */
	public void clear() {
		// TODO Auto-generated method stub

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#isEmpty()
	 */


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray(T[])
	 */
	public <T> T[] toArray(T[] array) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.ualberta.cs.c301f12t01.util.ObservableCollection#getAt(int)
	 */
	@Override
	public Report getAt(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.ualberta.cs.c301f12t01.util.ObservableCollection#replaceNoNotify(java
	 * .lang.Object, java.lang.Object)
	 */
	@Override
	protected boolean replaceNoNotify(Report oldElement, Report newElement) {
		/* YOU CAN'T REPLACE A REPORT ONCE IT IS MADE! */
		return false;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.ualberta.cs.c301f12t01.util.ObservableCollection#removeNoNotify(java
	 * .lang.Object)
	 */
	@Override
	protected boolean removeNoNotify(Report element) {
		/* YOU CAN'T REMOVE A REPORT ONCE IT IS MADE! */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.ualberta.cs.c301f12t01.util.ObservableCollection#removeKey(java.lang
	 * .Object)
	 */
	@Override
	public Report removeKey(UUID key) {
		return null;
	}

	/**
	 * let them get our instance
	 * 
	 * @return Singleton instance of TaskManager
	 */
	public static ReportManager getInstance() {
		return null;
	}

}
