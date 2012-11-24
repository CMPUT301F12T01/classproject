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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.TaskManager;


/**
 * DefineTaskActivity -- Displays the user interface for defining a task.
 * 
 * Displays the layout for activiy_define_task. Which displays a fragment of DefineTaskFragment 
 * {@link DefineTaskFragment} that handles defining a task.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>, Bronte Lee <bronte@ualberta.ca>
 *
 */
public class DefineTaskActivity extends Activity {


	/**
	 * Configures the home button and adds a button for the User o click when completing a Task
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_define_task);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		/*  Do we need anything if null? */
		if (savedInstanceState == null) {

		}
		
		 // DONE Add Log button
        Button button_add_done = (Button) findViewById(R.id.button_done_task);
        button_add_done.setOnClickListener(new OnClickListener() {
        	
            public void onClick(View arg0) {
            	
    			if (onTaskCreated()) {
    				// Go back to Main when done
    				finish();
    			}
            }
        });  

		android.util.Log.d("Act-LIFECYCLE", "DefineTaskActivity-onCreate");

	}

	
	// Returning boolean to indicated whether we return back or not	
	protected boolean onTaskCreated() {

		/* Get the views. Blame Java for the ugly, unreadable mess. */
		RadioGroup sharingButtons = (RadioGroup) findViewById(R.id.radio_group_sharing);
		EditText descriptionView = (EditText) findViewById(R.id.edit_description);
		EditText summaryView = (EditText) findViewById(R.id.edit_summary);
		ToggleButton text_toggle = (ToggleButton) findViewById(R.id.toggle_text);
		ToggleButton photo_toggle = (ToggleButton) findViewById(R.id.toggle_photo);
		ToggleButton audio_toggle = (ToggleButton) findViewById(R.id.toggle_audio);


		/* Extract the text fields. */
		String descriptionText = descriptionView.getText().toString();
		String summaryText = summaryView.getText().toString();

		/* We need to have a summary filled out! */
		if ( summaryText.equals("") ) {
			/* Dialog or Toast? */
			Toast.makeText(getBaseContext(), "Please fill out a summary", Toast.LENGTH_SHORT).show();
			return false;
		}

		/* Now set up the new task with the extracted data. */
		/* TODO: Get rid of this dummy user crud. */
		Task newTask = new Task(TaskSourceApplication.hack__user);

		newTask.setDescription(descriptionText);
		newTask.setSummary(summaryText);

		/* Set sharing. */
		switch (sharingButtons.getCheckedRadioButtonId()) {
		case R.id.radio_local:
			newTask.setLocal();
			break;
		case R.id.radio_global:
			newTask.setGlobal();
			break;
		}

		/* Is a toggle button boolean? We need one of these toggled! */
		boolean addARequest = false;

		/* Add requests. */
		if (text_toggle.isChecked()) {
			newTask.addRequest(new Request(MediaType.TEXT));
			addARequest = true;
		}
		if (photo_toggle.isChecked()) {
			newTask.addRequest(new Request(MediaType.PHOTO));
			addARequest = true;
		}
		if (audio_toggle.isChecked()) {
			newTask.addRequest(new Request(MediaType.AUDIO));
			addARequest = true;
		}

		/* So if we didn't add a request... */
		if (!addARequest) {
			/* Toast or Dialog? */
			Toast.makeText(getBaseContext(), "Please add a request.", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		android.util.Log.d("Act-LIFECYCLE", "DefineTaskActivity-onTaskCreated");
		
		// TODO: turn this back on! So saving occurs again!
		//add task using TaskManager
		TaskManager tm = ((TaskSourceApplication) getApplication()).getTaskManager();
		//tm.addTask(newTask);

		Toast.makeText(getBaseContext(), "Task created.", Toast.LENGTH_SHORT).show();
		
		return true;

	}

	/* May remove add button and re-add this later */
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_define_task, menu);
		return true;
	}
	*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;

		// Why can't we find this?
		/*
		case android.R.id.menu_done_define:
			onTaskCreated();
			return true;
		*/
			
		}
		
		return super.onOptionsItemSelected(item);
	}
}
