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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;

/**
 * The initial activity of our TaskSource App. It has three tabs for
 * showing "Your Tasks", "Local Tasks" and "Global Tasks"
 * 
 * @author Bronte Lee <bronte@ualberta.ca>
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public class TaskListActivity extends Activity implements
TaskListFragment.Callbacks, TabListener {

	public static final String ARG_TASK_ID = "task_id";
	
	/* Tab labels. Will remove if ActionBar Tab XML file gets created */
	public static final String USER_TASKS = "Your Tasks";
	public static final String LOCAL_TASKS = "Stored Tasks";
	public static final String GLOBAL_TASKS = "Global Tasks";
	
	/* Add some default Tags..though I might have missed a global declaration somewhere */
	public static final String USER = "USER";
	public static final String LOCAL = "LOCAL";
	public static final String GLOBAL = "GLOBAL";
	
	public static String ARG_NAME = "name";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);

		android.util.Log.d("Act-LIFECYCLE", "TaskListAcivity - onCreate ");

		actionBarTabSetup();

		if (savedInstanceState == null) {

			// by default, show stuff from user
			showListFrom(USER);
		}	
	}

	/** Sets up the ActionBar with tabs
	 * 
	 */
	/*
	 * TODO: Make this cleaner by setting up an XML file instead!
	 */
	public void actionBarTabSetup() {
		// Use the action bar for tabs
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set the text of the tab
		ActionBar.Tab userTasksTab = actionBar.newTab().setText(USER_TASKS);
		ActionBar.Tab localTasksTab = actionBar.newTab().setText(LOCAL_TASKS);
		ActionBar.Tab globalTasksTab = actionBar.newTab().setText(GLOBAL_TASKS);
		
		/* set the tag of each tab. This will be used to figure out which
		 * TaskCollection to use when displaying the list of tasks
		 */
		userTasksTab.setTag(USER);
		localTasksTab.setTag(LOCAL);
		globalTasksTab.setTag(GLOBAL);

		// Set the tabs to listen for any changes
		userTasksTab.setTabListener(this);
		localTasksTab.setTabListener(this);
		globalTasksTab.setTabListener(this);

		// add the tab to the action bar
		actionBar.addTab(userTasksTab);
		actionBar.addTab(localTasksTab);
		actionBar.addTab(globalTasksTab);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_task_list, menu);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/* Check if we're defining a task. */
		case R.id.menu_define_task:
			onClickDefineTask(item);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/**
	 * The list fragment calls this when an item in the list is selected
	 * (therefore spawning the task detail activity.
	 */
	public void onItemSelected(UUID taskId) {

		Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
		intent.putExtra(ARG_TASK_ID, taskId);

		android.util.Log.d("Act-LIFECYCLE", "TaskListAcivity - onItemSelected taskId " +
				taskId);

		startActivity(intent);

	}

	/** Starts the "define new task" screen. */
	public void onClickDefineTask(MenuItem item) {

		android.util.Log.d("Act-LIFECYCLE", "TaskListActivity-onClickDefineTask");

		Intent intent = new Intent(getBaseContext(), DefineTaskActivity.class);
		startActivity(intent);

	}

	/* (non-Javadoc)
	 * @see android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

		android.util.Log.d("Act-LIFECYCLE", "TaskListAcivity - onTabSelected tag: " +
				tab.getTag());		

		showListFrom((String)tab.getTag());
		

	}

	/* (non-Javadoc)
	 * @see android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
	
	/**Pass it the name of the task collection to display the tasks for the ListFragment
	 * 
	 * @param name
	 */
	public void showListFrom(String name){
		/* Tag contains the name of the TaskCollection we need */
		ARG_NAME = name;
		
		android.util.Log.d("Act-LIFECYCLE", "TaskListAcivity - showListFrom: " +
				name);	
		
		Bundle arguments = new Bundle();

		arguments.putSerializable(TaskListFragment.ARG_NAME,
				getIntent().getSerializableExtra(TaskListFragment.ARG_NAME));
		
		TaskListFragment fragment = new TaskListFragment();
		
		fragment.setArguments(arguments);
		getFragmentManager().beginTransaction()
			.add(R.id.task_list, fragment)
			.commit();
	}

}
