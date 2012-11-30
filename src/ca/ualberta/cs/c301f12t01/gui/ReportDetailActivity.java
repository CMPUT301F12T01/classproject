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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Response;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.gui.helper.ResponseObtainer;
import ca.ualberta.cs.c301f12t01.gui.helper.ResponseObtainerDisplayer;

/** ReportDetailActivity - Displays the responses of the report
 * 
 * @author Bronte Lee <bronte@ualberta.ca>
 *
 */
public class ReportDetailActivity extends Activity {

	/* We need the taskId to get the reports and the reportId
	 * to get the get report...we shouldn't have to do this.
	 */
	public static final String ARG_REPORT_ID = "report_id";
	public static final String ARG_TASK_ID = "task_id";

	private Report report;

	ArrayList<ResponseObtainer> obtainers = new ArrayList<ResponseObtainer>();

	/** onCreate - obtains information from the ReportListActivity and 
	 * displays the report's responses. 
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		/* The list of tasks Activity passed us a taskID, so we should get it*/
		Bundle taskBundle = getIntent().getExtras();		
		if (taskBundle != null) {

			UUID reportId = (UUID) taskBundle.getSerializable(ARG_REPORT_ID);
			UUID taskId = (UUID) taskBundle.getSerializable(ARG_TASK_ID);

			Task task = TaskSourceApplication.getTask(taskId);

			TaskSourceApplication.getReports(task);

			List<Report> reports = TaskSourceApplication.getReports(task);

			// find the right report...shouldn't be here
			for (Report r : reports) {
				if (r.getId().equals(reportId)) {
					report = r;
				}
			}


			android.util.Log.d("Act-LIFECYCLE", "TaskDetailAcivity - onCreated reportId " +
					reportId);
		}

		/* Now display the report information! */
		displayReportInformation();


	}

	/** Display the content of the report. Time stamp, responses
	 * 
	 */
	protected void displayReportInformation() {

		android.util.Log.d("Act-LIFECYCLE", "TaskDetailAcivity - displayReportInformation");

		/* Set the time stamp. */
		((TextView) findViewById(R.id.text_timestamp_response)).setText( 
				report.getTimestamp().toString());

		/* Get the container for the responses. */
		ViewGroup responseContainer = (ViewGroup) findViewById(R.id.report_responses);
		LayoutInflater inflater = getLayoutInflater();

		/* Show the responses of the report...first, inflate the proper fragment */
		for (Response response : report) {
			obtainers.add(ResponseObtainerDisplayer.showResponseDisplayer(
							response, inflater, responseContainer));
		}
	}

	/* If a menu option is selected */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
