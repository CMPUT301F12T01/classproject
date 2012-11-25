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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ca.ualberta.cs.c301f12t01.common.AudioResponse;
import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.PhotoResponse;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Response;
import ca.ualberta.cs.c301f12t01.common.Sharing;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.common.TextResponse;

/**
 * This class is responsible for reconstructing
 * the Reports from the database.
 * 
 * This includes assembling the Responses
 * associated with each Report and 
 * disassembling those Responses
 * 
 * IMPORTANT!!!!
 * This class cannot guarantee that response
 * objects can be recovered at the present time.
 * 
 * @author Neil Borle
 *
 */
public class ReportLocalRetrieval {
	
	/**
	 * This method will give you all of the Reports that are associated
	 * with a particular task. By taking a Sharing state you can select
	 * for Reports of a particular scope
	 * 
	 * @param SQLiteDatabase db
	 * @param UUID taskid
	 * @param Sharing state
	 * @return ArrayList<Report>
	 */
	public static ArrayList<Report> getReports(SQLiteDatabase db, Task matchingTask, Sharing state) {
		/**
		 * 
		 */
		String reportTable = "Reports";
		String responseTable = "Responses";
		String []reportSelectCollumns = {"task_id", "id", "scope", "timestamp"};
		String []responseSelectCollumns = {"report_id", "mediatype", "media"};
		ArrayList<Report> reportList = new ArrayList<Report>();

		Cursor scopedReports;
		
		if (state != null){
			// Get all Reports in a given scope {LOCAL, GLOBAL or TASK_CREATOR}
			scopedReports = db.query(reportTable, 
				reportSelectCollumns, reportSelectCollumns[2] + " = '" + state.toString() + "' "
				+ "AND " + reportSelectCollumns[0] + " = '" + matchingTask.getId().toString() + "'", 
				null, null, null, null);
		}
		else {
			// Get all Reports given the task they belong to
			scopedReports = db.query(reportTable, 
				reportSelectCollumns, reportSelectCollumns[0] + " = '" + 
				matchingTask.getId().toString() + "'", null, null, null, null);
			
		}
		
		// Create a new report with a all the fields set
		for (scopedReports.moveToFirst(); !scopedReports.isAfterLast(); scopedReports.moveToNext()) {
			Report newReport = new Report(
					UUID.fromString(scopedReports.getString(
							scopedReports.getColumnIndex(reportSelectCollumns[0]))),
					UUID.fromString(scopedReports.getString(
							scopedReports.getColumnIndex(reportSelectCollumns[1]))),
					Timestamp.valueOf(scopedReports.getString(
							scopedReports.getColumnIndex(reportSelectCollumns[3]))));

			newReport.setSharing(state);


			// GET ALL RESPONSES RELATED TO A REPORT
			Cursor reportResponses = db.query(responseTable, responseSelectCollumns, 
					responseSelectCollumns[0] + " = '" + scopedReports.getString(
							scopedReports.getColumnIndex(reportSelectCollumns[1])) + "'", 
					null, null, null, null);

			for (reportResponses.moveToFirst(); !reportResponses.isAfterLast(); reportResponses.moveToNext()) {

				Response newResponse;

				// Figure out what type of response it is, instantiate it and append it to the report's list
				if (MediaType.valueOf(reportResponses.getString(
						reportResponses.getColumnIndex(responseSelectCollumns[1]))) == MediaType.AUDIO) {
					
					newResponse = new AudioResponse();
				
				}
				else if (MediaType.valueOf(reportResponses.getString(
						reportResponses.getColumnIndex(responseSelectCollumns[1]))) == MediaType.PHOTO) {
					
					newResponse = new PhotoResponse(null, null);
				
				}
				else {
					newResponse = new TextResponse(null);
				}

				// Create streams to pull out the response blob
				ByteArrayInputStream bis = new ByteArrayInputStream(reportResponses.getBlob(
						reportResponses.getColumnIndex(responseSelectCollumns[2])));
				
				ObjectInput in = null;
				try {
					in = new ObjectInputStream(bis);
					newResponse.setResponseData((Serializable) in.readObject());

					bis.close(); 
					in.close(); 
				} catch (IOException e) {
					e.printStackTrace(); 
				} catch (ClassNotFoundException e){
					e.printStackTrace(); 
				} 

				newReport.addResponse(newResponse);
			}

			reportList.add(newReport);
		}

		return reportList;
	}

}
