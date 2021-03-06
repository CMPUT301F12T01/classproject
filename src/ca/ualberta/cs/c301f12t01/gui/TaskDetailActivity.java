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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.util.StringUtils;

/**
 * TaskDetailActivity -- Displays the user interface for Task Details.
 * 
 * Displays the layout for activity_task_detail.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * @author Bronte Lee <bronte@ualberta.ca>
 * 
 */
public class TaskDetailActivity extends Activity {

	/* TODO: Get some more global place to store the following string. */
	public static final String ARG_TASK_ID = "task_id";

	private UUID taskId;
	private Task task;

	/**
	 * onCreate - show layout, set home button, get information from
	 * TaskListActivity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		/* The list of tasks Activity passed us a taskID, so we should get it */
		Bundle taskBundle = getIntent().getExtras();
		if (taskBundle != null) {

			taskId = (UUID) taskBundle.getSerializable(ARG_TASK_ID);

			task = TaskSourceApplication.getTask(taskId);

		}

		/* Now display the task information! */
		displayTaskInformation();
	}

	@Override
	protected void onResume() {
		super.onResume();

		/* Now display the task information! */
		task = TaskSourceApplication.getTask(taskId);
		displayTaskInformation();
	}

	/* Displays the task's information */
	protected void displayTaskInformation() {
		TextView descriptionView = (TextView) findViewById(R.id.task_description);
		TextView summaryView = (TextView) findViewById(R.id.task_summary);
		TextView requirementView = (TextView) findViewById(R.id.task_requests);
		TextView sharingView = (TextView) findViewById(R.id.task_sharing_setting);

		/* Collect strings and stuff from the Task instance. */
		String descriptionText = task.getDescription();
		String summaryText = task.getSummary();

		String requirementText;
		Set<MediaType> requirementSet = new HashSet<MediaType>();
		/* Setup the requirement text. */
		for (Request request : task) {
			requirementSet.add(request.getType());
		}
		requirementText = StringUtils.joinToString(requirementSet,
				getText(R.string.list_seperator));
		/* It's over. The horror is over. There's nothing to see here, kids. */

		/* Set the associated views. */
		summaryView.setText(summaryText);

		/* Set the description text. */
		if (descriptionText.equals("")) {
			/*
			 * Description is empty. State that there is no description instead
			 * of giving the user a blank screen.
			 */
			descriptionView.setText(getText(R.string.task_no_description));
			descriptionView.setTypeface(Typeface
					.defaultFromStyle(Typeface.ITALIC));
		} else {
			descriptionView.setText(descriptionText);
		}

		requirementView.setText(requirementText);

		/* Get the sharing option */
		if (task.isGlobal()) {
			sharingView.setText(getString(R.string.task_global));
		} else {
			sharingView.setText(getString(R.string.task_local));
		}
	}

	/**
	 * When the user wants to view reports, start the report viewing activity.
	 */
	private void onUserSelectViewReports() {
		if (!TaskSourceApplication.taskHasReports(task.getId())) {
			Toast.makeText(getBaseContext(), R.string.task_has_no_reports,
					Toast.LENGTH_SHORT).show();
			return;
		}

		startActivityWithTask(ReportListActivity.class);
	}

	/**
	 * When the user wants to fulfill reports, start the task fulfillment
	 * activity.
	 */
	private void onUserSelectFulfill() {
		startActivityWithTask(FulfillTaskActivity.class);
	}

	private void onUserSelectEdit() {
		/* Replace with a better test later */
		startActivityWithTask(EditTaskActivity.class);
	}

	/* TODO: This should really not be in *this* class. */
	@SuppressWarnings("rawtypes")
	private void startActivityWithTask(Class activity) {

		Intent intent = new Intent(getApplicationContext(), activity);
		intent.putExtra(ARG_TASK_ID, task.getId());
		startActivity(intent);
	}

	private void showDeleteTaskDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage("Are you sure you want to delete this task?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								TaskDetailActivity.this.finish();
								// TODO: need something like this!
								TaskSourceApplication.removeTask(task);
								Toast.makeText(getBaseContext(),
										R.string.task_delete_success,
										Toast.LENGTH_SHORT).show();
								finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}

	/* Display menu */
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_task_detail, menu);

		/*
		 * If you are NOT the user that created this task, you can't edit or
		 * delete it
		 */
		if (!TaskSourceApplication.getUserID().equals(task.getUser())) {
			menu.findItem(R.id.menu_edit_task).setEnabled(false);
			menu.findItem(R.id.menu_delete_task).setEnabled(false);
		}

		return true;
	}

	/* When a menu item is selected */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.menu_fulfill_task:
			onUserSelectFulfill();
			return true;
		case R.id.menu_view_reports:
			onUserSelectViewReports();
			return true;
		case R.id.menu_edit_task:
			onUserSelectEdit();
			return true;
		case R.id.menu_delete_task:
			showDeleteTaskDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
