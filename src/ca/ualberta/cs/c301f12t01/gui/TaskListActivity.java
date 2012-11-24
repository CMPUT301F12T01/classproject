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

import java.io.Serializable;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * 
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 *
 */
public class TaskListActivity extends Activity implements
TaskListFragment.Callbacks {

	/** Denotes that the screen has two panes. */
	private boolean hasTwoPanes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);

		/* See if the view is using the two pane layout. */
		if (findViewById(R.id.task_detail_container) != null) {

			hasTwoPanes = true;

			((TaskListFragment) getFragmentManager().findFragmentById(
					R.id.task_list)).setActivateOnItemClick(true);
		}

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
			/* That's it. actually. */
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/**
	 * The list fragment calls this when an item in the list is selected
	 * (therefore spawning the detail fragment.
	 */
	public void onItemSelected(UUID taskId) {
		startNewFragment(TaskDetailFragment.class, TaskDetailActivity.class,
		        TaskDetailFragment.ARG_TASK_ID, taskId);
	}

	/** Starts the "define new task" screen. */
	public void onClickDefineTask(MenuItem item) {

		android.util.Log.d("Act-LIFECYCLE", "TaskListActivity-onClickDefineTask");

		Intent intent = new Intent(getBaseContext(), DefineTaskActivity.class);
		startActivity(intent);


	}

	/*
	 * TODO: Create a superclass that contains the following two methods
	 * because... geez. Maybe call it `TwoPaneActivity`.
	 */

	/* TODO: Call the next two methods `startNewDetail` or something... /

    /**
	 * A shortcut for {@link #startNewFragment(Class, Class, String,
	 * Serializable}, but without the need of any String key/Serializable
	 * schenanigans.
	 * 
	 * @see #startNewFragment(Class, Class, String, Serializable)
	 */
	@SuppressWarnings("rawtypes")
	protected void startNewFragment(Class fragmentClass,
			Class fragmentActivityClass) {
		startNewFragment(fragmentClass, fragmentActivityClass, null, null);
	}

	/**
	 * Starts a new fragment in the appropriate place. If the screen has one
	 * pane (as with a mobile phone), an intent is created with the given
	 * FragmentActivity. Else, if the screen has two panes (such as with a large
	 * tablet), the given Fragment is started in the detail pane.
	 * 
	 * Some fragments require some extra information. This is usually one
	 * key/value pair. You may provide the String key and an arbitrary
	 * {@link Serializable} value. If this behavior is not required, pass in
	 * <code>null</code> for the key. One may also use the two-argument method
	 * of the same name instead.
	 * 
	 * @param fragmentClass
	 *            The {@link Fragment} that needs to be created in the detail
	 *            pane.
	 * @param fragmentActivityClass
	 *            The {@link FragmentActivity} that should be spawned if there
	 *            is no detail pane.
	 * @param key
	 *            The key attached either to the {@link FragmentTransaction} or
	 *            the Activity spawning {@link Intent}. Set this to
	 *            <code>null</code> if not needed.
	 * @param arbitraryValue
	 *            The {@link Serializable} value attached.
	 */
	@SuppressWarnings("rawtypes")
	protected void startNewFragment(Class fragmentClass,
			Class fragmentActivityClass, String key, Serializable arbitraryValue) {

		if (hasTwoPanes) {

			Bundle arguments = new Bundle();

			/* If the user wants an extra value to tag along... */
			if (key != null) {
				arguments.putSerializable(key, arbitraryValue);
			}

			Fragment fragment;
			try {
				fragment = (Fragment) fragmentClass.newInstance();
				fragment.setArguments(arguments);
				getFragmentManager().beginTransaction()
				.replace(R.id.task_detail_container, fragment).commit();
			} catch (InstantiationException e) {
				/* I DON'T KNOW WHAT TO DO HERE. */
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				/* OR HERE. */
				e.printStackTrace();
			}

		} else {
			Intent detailIntent = new Intent(this, fragmentActivityClass);

			if (key != null) {
				detailIntent.putExtra(key, arbitraryValue);
			}

			startActivity(detailIntent);
		}
	}

}
