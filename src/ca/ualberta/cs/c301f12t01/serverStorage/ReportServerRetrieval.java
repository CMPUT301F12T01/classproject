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
package ca.ualberta.cs.c301f12t01.serverStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Sharing;
import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * 
 * @author home
 * 
 */
public class ReportServerRetrieval {

	/**
	 * 
	 * @return all simple server objects on server
	 */
	public static ReportServerObj[] getAllSO() {
		// first make the call to the server
		List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
		nvp.add(new BasicNameValuePair("action", "list"));
		Server server = new Server();
		String jsonString = server.post(nvp);
		// Now convert to an array of server objects
		Gson gson = new Gson();
		ReportServerObj[] allSO = gson.fromJson(jsonString,
				ReportServerObj[].class);
		return allSO;
	}

	/**
	 * 
	 * @return all reports on the server
	 */
	public static ArrayList<Report> getAllReports() {
		ReportServerObj[] allSO = getAllSO();
		ArrayList<Report> reports = new ArrayList<Report>();
		// iterate through and grab task
		for (ReportServerObj so : allSO) {
			if (so.getSummary().equals("report")) {
				Report r = getContentFromServer(so.getId());
				reports.add(r);
			}
		}
		return reports;
	}

	/**
	 * @param matchingTask
	 *            Task that reports should belong to
	 * @return all reports on the server that match matchingTask
	 */
	public static ArrayList<Report> getAllReports(Task matchingTask) {
		ReportServerObj[] allSO = getAllSO();
		ArrayList<Report> reports = new ArrayList<Report>();
		// iterate through and grab task
		for (ReportServerObj so : allSO) {
			if (so.getSummary().equals("report")) {
				Report r = getContentFromServer(so.getId());
				if (r.getTaskID().equals(matchingTask.getId())) {
					reports.add(r);
				}
			}
		}
		return reports;
	}

	/**
	 * 
	 * @param taskID
	 *            UUID that report's taskID should match
	 * @return ArrayList of reports that match taskID
	 */
	public static ArrayList<Report> getTaskReports(UUID taskID) {
		ArrayList<Report> al = getAllReports();
		ArrayList<Report> task = new ArrayList<Report>();
		for (Report r : al) {
			if (r.getTaskID().equals(taskID)) {
				task.add(r);
			}
		}
		return task;
	}

	/**
	 * 
	 * @param matchingTask
	 *            Task that reports should belong to
	 * @return ArrayList of global reports
	 */
	public static ArrayList<Report> getGlobalReports(Task matchingTask) {
		ArrayList<Report> al = getAllReports();
		ArrayList<Report> toReturn = new ArrayList<Report>();
		for (Report r : al) {
			if (r.getSharing().equals(Sharing.GLOBAL)
					&& r.getTaskID().equals(matchingTask.getId())) {
				toReturn.add(r);
			}
		}
		return toReturn;
	}

	/**
	 * 
	 * @param matchingTask
	 *            Task that reports should belong to
	 * @return ArrayList of local reports
	 */
	public static ArrayList<Report> getLocalReports(Task matchingTask) {
		ArrayList<Report> al = getAllReports();
		ArrayList<Report> toReturn = new ArrayList<Report>();
		for (Report r : al) {
			if (r.getSharing().equals(Sharing.LOCAL)
					&& r.getTaskID().equals(matchingTask.getId())) {
				toReturn.add(r);
			}
		}
		return toReturn;
	}

	/**
	 * Method works to get reports and tasks
	 * 
	 * @param id
	 *            Server ID of the content we want to get
	 * @return content from server
	 */
	public static Report getContentFromServer(String id) {
		// create our nvp
		List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
		nvp.add(new BasicNameValuePair("action", "get"));
		nvp.add(new BasicNameValuePair("id", id));
		// post
		Server server = new Server();
		String jsonString = server.post(nvp);
		// convert to SO
		Gson gson = new Gson();
		ReportServerObj so = gson.fromJson(jsonString, ReportServerObj.class);
		return so.getContent();
	}

}
