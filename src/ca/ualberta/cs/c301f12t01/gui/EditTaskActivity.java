package ca.ualberta.cs.c301f12t01.gui;

import java.util.UUID;

import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class EditTaskActivity extends Activity {

	public static final String ARG_TASK_ID = "task_id";

	private Task task;
	

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* We can use the same activity layout */
		setContentView(R.layout.activity_define_task);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		/* The list of tasks Activity passed us a taskID, so we should get it*/
		Bundle taskBundle = getIntent().getExtras();		
		if (taskBundle != null) {
			
			UUID taskId = (UUID) taskBundle.getSerializable(ARG_TASK_ID);
			
			task = TaskSourceApplication.getTask(taskId);
			
			android.util.Log.d("Act-lc", "EditTaskActivity - TaskId: " + taskId);
			
		}
		
		displayOldTaskInformation();
		
	}

	/** Displays the old task's information
	 * 
	 */
	private void displayOldTaskInformation() {
		// TODO Auto-generated method stub
		/* Collect strings and stuff from the Task instance. */
		
		/* Get the views */
		RadioGroup sharingButtons = (RadioGroup) findViewById(R.id.radio_group_sharing);
		EditText descriptionView = (EditText) findViewById(R.id.edit_description);
		EditText summaryView = (EditText) findViewById(R.id.edit_summary);
		ToggleButton text_toggle = (ToggleButton) findViewById(R.id.toggle_text);
		ToggleButton photo_toggle = (ToggleButton) findViewById(R.id.toggle_photo);
		ToggleButton audio_toggle = (ToggleButton) findViewById(R.id.toggle_audio);
		
		
		/* Set summary and description */
		String descriptionText = task.getDescription();
		String summaryText = task.getSummary();
		
		summaryView.setText(summaryText);
		descriptionView.setText(descriptionText);
		
		/* Show previous requirements */
		for (Request request : task) {
			MediaType media = request.getType();
			switch(media) {
				case TEXT:
					text_toggle.setChecked(true);
					break;
				case PHOTO:
					photo_toggle.setChecked(true);
					break;
				case AUDIO:
					audio_toggle.setChecked(true);
					break;
			}
					
		}
		
		/* Display previous sharing settings. By default it's local (by layout) */
		
		if ( task.isGlobal() ) {
			RadioButton globalRadioButton = (RadioButton) findViewById(R.id.radio_global);
			globalRadioButton.setChecked(true);
		}
		
		
	}

	/** onTaskCreated - checks to see if the minimum fields 
	 * have been filled in and then get's the task's 
	 * information 
	 * 
	 * @return True if the minimum fields have been filled out.
	 * 			False if the minimum fields have not been filled out.
	 */
	protected boolean onTaskUpdated() {
		/* Extract the text fields. */
		
		/* Get the views */
		RadioGroup sharingButtons = (RadioGroup) findViewById(R.id.radio_group_sharing);
		EditText descriptionView = (EditText) findViewById(R.id.edit_description);
		EditText summaryView = (EditText) findViewById(R.id.edit_summary);
		ToggleButton text_toggle = (ToggleButton) findViewById(R.id.toggle_text);
		ToggleButton photo_toggle = (ToggleButton) findViewById(R.id.toggle_photo);
		ToggleButton audio_toggle = (ToggleButton) findViewById(R.id.toggle_audio);
		
		String descriptionText = descriptionView.getText().toString();
		String summaryText = summaryView.getText().toString();

		/* We need to have a summary filled out! */
		if ( summaryText.equals("") ) {
			/* Dialog or Toast? */
			Toast.makeText(getBaseContext(), "Please fill out a summary", Toast.LENGTH_SHORT).show();
			return false;
		}

		/* Now set up the new task with the extracted data. */
		Task newTask = TaskSourceApplication.newTaskForCurrentUser();

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

		TaskSourceApplication.modifyTask(task, newTask);
		Toast.makeText(getBaseContext(), "Task updated?", Toast.LENGTH_SHORT).show();

		return true;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_edit_task, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.menu_done:
			if (onTaskUpdated()) {
				finish();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
