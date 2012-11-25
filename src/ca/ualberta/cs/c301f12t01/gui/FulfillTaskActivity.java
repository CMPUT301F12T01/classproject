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
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Sharing;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.dummy.DummyTasks;
import ca.ualberta.cs.c301f12t01.gui.helper.ResponseObtainer;
import ca.ualberta.cs.c301f12t01.gui.helper.ResponseObtainerObtainer;
import ca.ualberta.cs.c301f12t01.model.ReportManager;
import ca.ualberta.cs.c301f12t01.model.TaskManager;

/**
 * FulfillTaskActivity -- Displays the user interface for Fulfilling a Task.
 * 
 * Displays the layout for activity_fulfill_task.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>, Bronte Lee <bronte@ualberta.ca>
 *
 */

public class FulfillTaskActivity extends Activity {

	
	/* TODO: Get some more global place to store the following string. */
	public static final String ARG_TASK_ID = "task_id";

	private Task task;
	
	ArrayList<ResponseObtainer> obtainers = new ArrayList<ResponseObtainer>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill_task);

        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        /* The list of tasks Activity passed us a taskID, so we should get it*/
		Bundle taskBundle = getIntent().getExtras();		
		if (taskBundle != null) {
			
			UUID taskId = (UUID) taskBundle.getSerializable(ARG_TASK_ID);

			/* TODO: REMOVE DUMMY */
			//TaskManager tm = ((TaskSourceApplication) getApplication()).getTaskManager();
			//task = tm.get(taskId);
			task = DummyTasks.ITEM_MAP.get(taskId);
			
		}

		/* Display Fulfilling Task information */
		displayFulfillTaskInformation();


    }
    
    protected void displayFulfillTaskInformation() {
    	
    	/* Sets the simple text views. */
        ((TextView) findViewById(R.id.task_summary)).setText(task
                .getSummary());
        String descriptionText = task.getDescription();
        
        TextView descripionView = (TextView) findViewById(R.id.task_description);
        
        if (descriptionText.equals("")) {
            /* No description? Don't show the text widget at all! */
            descripionView.setVisibility(View.GONE);
        } else {
            descripionView.setText(descriptionText);
        }
    
        /* Get the container for the responses. */
        ViewGroup responseContainer = (ViewGroup) findViewById(R.id.task_responses);
        

        /* Er, this displays the proper response fields, right? 
         * Leaving this to Eddie to  display the proper fields!
         */
        
        /* Creates a response field for each respective request, and remember them. */
        /*
        for (Request request : task) {
            obtainers.add(
                ResponseObtainerObtainer.getResponseObtainer(
                    request.getType(), inflater, responseContainer));
        }
    	*/
    	
    }

    
    
    /**
     * Called when the user decides they are done fulfilling the task.
     * 
     * @returns false is the form was completed incorrectly, else true.
     */
    protected Boolean onFormCompletion() {
        
        Report report = new Report(task);
        
        attachReportSharingMode(report);
        attachResponsesToReport(report);

        ReportManager.getInstance().addReport(report);

        return true;
    }
    
    /**
     * Attaches the sharing mode on the current activity
     * to the given Report.
     * 
     * @param report
     */
    private void attachReportSharingMode(Report report) {

        RadioGroup sharingButtons = (RadioGroup) findViewById(R.id.radio_group_send_options);

        Sharing selectedSharing = Sharing.TASK_CREATOR;

        switch (sharingButtons.getCheckedRadioButtonId()) {
        case R.id.radio_send_to_owner:
            selectedSharing = Sharing.TASK_CREATOR;
            break;
        case R.id.radio_send_to_server:
            selectedSharing = Sharing.GLOBAL;
            break;
        case R.id.radio_do_not_send:
            selectedSharing = Sharing.LOCAL;
            break;
        }

        report.setSharing(selectedSharing);
        
    }
    
    /**
     * Attaches every Response on the current activity
     * to the given Report.
     * 
     * @param report
     */
    private void attachResponsesToReport(Report report) {
        for (ResponseObtainer obtainer : obtainers) {
            report.addResponse(obtainer.getResponse());
        }
    }
    
    
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_fulfill_task, menu);
		return true;
	}
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        case R.id.menu_done:
            //onFormCompletion();
            Toast.makeText(getBaseContext(), "Report soon to be saved", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
