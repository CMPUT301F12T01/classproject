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
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.gui.helper.RandomTaskGenerator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;

/**
 * The initial activity of our TaskSource App. It has three tabs for showing
 * "Your Tasks", "Local Tasks" and "Global Tasks"
 * 
 * @author Bronte Lee <bronte@ualberta.ca>
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
/*
 * TODO: move the tab stuff into another class.
 */
public class TaskListActivity extends Activity implements
		TaskListFragment.Callbacks, TabListener {

	private static final String ARG_TASK_ID = "task_id";
	private static final String CURRENT_TAB_INDEX = "current tab index";

	private RandomTaskGenerator randomTaskGenerator = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_task_list);

		android.util.Log.d("Act-LIFECYCLE", "TaskListAcivity - onCreate ");

		/* Initialize the random generator with local and global tasks. */
		randomTaskGenerator = new RandomTaskGenerator(
				TaskSourceApplication.getLocalTaskCollection(),
				TaskSourceApplication.getGlobalTaskCollection());

		setupActionBarTab();
	}

	/**
	 * Sets up the ActionBar with tabs
	 * 
	 */
	/*
	 * TODO: Make this cleaner by setting up an XML file instead!
	 */
	public void setupActionBarTab() {
		// Use the action bar for tabs
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set the text of the tab
		ActionBar.Tab userTasksTab = actionBar.newTab().setText(
		/* I'll teach you not to let me put an apostrophe in an identifier! */
		getText(R.string.失せるのタスクつ));
		ActionBar.Tab localTasksTab = actionBar.newTab().setText(
				getText(R.string.local_tasks));
		ActionBar.Tab globalTasksTab = actionBar.newTab().setText(
				getText(R.string.global_tasks));

		/*
		 * set the tag of each tab. This will be used to figure out which
		 * TaskCollection to use when displaying the list of tasks This needs to
		 * match with TaskCollection's names!
		 */
		userTasksTab.setTag("user");
		localTasksTab.setTag("local");
		globalTasksTab.setTag("global");

		// Set the tabs to listen for any changes
		userTasksTab.setTabListener(this);
		localTasksTab.setTabListener(this);
		globalTasksTab.setTabListener(this);

		// add the tab to the action bar
		actionBar.addTab(userTasksTab);
		actionBar.addTab(localTasksTab);
		actionBar.addTab(globalTasksTab);

	}

	/** This will keep track of which tab we were on */
	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		android.util.Log.d("Act-LIFECYCLE",
				"TaskListActivity-onSaveInstanceState");

		outBundle.putInt(CURRENT_TAB_INDEX, getActionBar()
				.getSelectedNavigationIndex());

	}

	/**
	 * This will display the last viewed tab (if you like changing the
	 * orientation...)
	 */
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {

		android.util.Log.d("Act-LIFECYCLE",
				"TaskListActivity-onRestoreInstanceState");

		if (savedInstanceState.containsKey(CURRENT_TAB_INDEX)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(CURRENT_TAB_INDEX));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar
	 * .Tab, android.app.FragmentTransaction)
	 */
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

		/* If the user selects the same tab, do nothing */

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar
	 * .Tab, android.app.FragmentTransaction)
	 */
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		android.util.Log.d("Act-LIFECYCLE",
				"TaskListAcivity - onTabSelected tag: " + tab.getTag());

		showListFrom((String) tab.getTag());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar
	 * .Tab, android.app.FragmentTransaction)
	 */
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		android.util.Log.d("Act-LIFECYCLE",
				"TaskListAcivity - onTabUNSelected tag: " + tab.getTag());

	}

	/**
	 * Pass it the name of the task collection to display the tasks for the
	 * ListFragment
	 * 
	 * @param Fragment
	 *            fragment, Sting name
	 */
	public void showListFrom(String name) {

		Bundle arguments = new Bundle();
		arguments.putSerializable(TaskListFragment.ARG_NAME, name);

		/*
		 * Wait... we create a new TaskListFragment each time we click a
		 * different tab? This is not an issue now, but it seems... troublesome.
		 */
		TaskListFragment fragment = new TaskListFragment();

		fragment.setArguments(arguments);

		getFragmentManager().beginTransaction()
				.replace(R.id.task_list, fragment).commit();
	}

	/** Starts the "define new task" screen. */
	public void onClickDefineTask(MenuItem item) {

		android.util.Log.d("Act-LIFECYCLE",
				"TaskListActivity-onClickDefineTask");

		Intent intent = new Intent(getBaseContext(), DefineTaskActivity.class);
		startActivity(intent);

	}

	public void onClickRandomTask(MenuItem item) {
		android.util.Log.d("Act-LIFECYCLE",
				"TaskListActivity-onClickRandomTask");

		UUID randomTaskID = randomTaskGenerator.getRandomTaskId();

		if (randomTaskID != null) {
			/* Pretend we selected an item the standard way. */
			onItemSelected(randomTaskID);
		} else {
			Toast.makeText(getBaseContext(), R.string.no_task_message,
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * The list fragment calls this when an item in the list is selected
	 * (therefore spawning the task detail activity.
	 */
	public void onItemSelected(UUID taskId) {

		Intent intent = new Intent(getApplicationContext(),
				TaskDetailActivity.class);
		intent.putExtra(ARG_TASK_ID, taskId);

		android.util.Log.d("Act-LIFECYCLE",
				"TaskListAcivity - onItemSelected taskId " + taskId);

		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_task_list, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/* Check if we're defining a task. */
		case R.id.menu_define_task:
			onClickDefineTask(item);
			return true;
		case R.id.menu_random_task:
			onClickRandomTask(item);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
