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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Response;


/**
 * This class is responsible for decomposing 
 * responses by attributes to be stored into 
 * the database
 * 
 * IMPORTANT!!!!
 * This class cannot guarantee that response
 * objects can be stored at the present time.
 * 
 * @author Neil Borle
 *
 */
public class ReportLocalStorage
{
	/**
	 * Stores a Report to the local SQLiteDatabase.
	 * 
	 * @param SQLiteDatabase db
	 * @param reportToStore
	 */
	public static void storeReport(SQLiteDatabase db, Report reportToStore) {

		String reportTable = "Reports";
		String responseTable = "Responses";
		String []reportCollumns = {"task_id", "id", "scope", 
		"timestamp"};
		String []responseCollumns = {"report_id", "mediatype",
		"media"};

		// Get all the fields from the task 
		String taskId = reportToStore.getTaskID().toString();
		String id = reportToStore.getId().toString();
		String state = reportToStore.getSharing().toString();
		String timestamp = reportToStore.getTimestamp().toString();

		// store all the extracted fields into the database
		ContentValues reportValues = new ContentValues();
		reportValues.put(reportCollumns[0], taskId);
		reportValues.put(reportCollumns[1], id);
		reportValues.put(reportCollumns[2], state);
		reportValues.put(reportCollumns[3], timestamp);

		//TODO ERROR HANDLING IF db.insert returns -1
		db.insert(reportTable, null, reportValues);

		for (Response response : reportToStore) {

			ContentValues requestValues = new ContentValues();
			requestValues.put(responseCollumns[0], 
					reportToStore.getId().toString());
			requestValues.put(responseCollumns[1], 
					response.getMediaType().toString());

			// Need to turn the response into a byte array to blob it
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutput objectOutput = null;
			byte[] responseBytes;
			try {
				objectOutput = new ObjectOutputStream(baos);   
				objectOutput.writeObject(response.getResponseData());
				responseBytes = baos.toByteArray();
				requestValues.put(responseCollumns[2], responseBytes);
				objectOutput.close();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			db.insert(responseTable, null, requestValues);

		}
	}

}
