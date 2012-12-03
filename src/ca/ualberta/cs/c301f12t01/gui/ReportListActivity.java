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

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import ca.ualberta.cs.c301f12t01.R;

/** ReportListActivty - displays all the reports of a task
 * 
 * @author Bronte Lee <bronte@ualberta.ca>
 *
 */
public class ReportListActivity extends Activity implements
ReportListFragment.Callbacks {

	private static final String ARG_REPORT_ID = "report_id";

	//private UUID taskId;

	/** onCreate - displays home/back button, displays the layout, 
	 * get information from the TaskDetailActivity
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_report_list);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
		
			/* Pass information to the ReportListFragment */
			Bundle arguments = new Bundle();

			/* Pass along the Task ID to the fragment */
			arguments.putSerializable(ReportListFragment.ARG_TASK_ID,
					getIntent().getSerializableExtra(ReportListFragment.ARG_TASK_ID));

			ReportListFragment fragment = new ReportListFragment();

			fragment.setArguments(arguments);

			getFragmentManager().beginTransaction()
			.add(R.id.report_list, fragment)
			.commit();
		}
	}


	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.gui.ReportListFragment.Callbacks#onItemSelected(java.util.UUID)
	 */
	public void onItemSelected(UUID reportId) {

		/* A report has been selected, so it's time to display the report's responses */
		Intent intent = new Intent(getApplicationContext(), ReportDetailActivity.class);
		intent.putExtra(ARG_REPORT_ID, reportId);


		android.util.Log.d("Act-LIFECYCLE", "ReportListAcivity - onItemSelected reportId " +
				reportId);

		startActivity(intent);

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
