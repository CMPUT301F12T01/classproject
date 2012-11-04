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

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.dummy.DummyTasks;

/**
 * DefineTaskFragment -- 
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 *
 */
public class DefineTaskFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Initialize stuff if it hasn't been initialized yet. */
        if (savedInstanceState == null) {
            setHasOptionsMenu(true);
        }
    }
    
    /** Called when the user confirms that the fields make up the new task. */
    protected void onTaskCreated() {
        View taskForm = getView();
        
        /* Get the views. Blame Java for the ugly, unreadable mess. */
        RadioGroup sharingButtons = (RadioGroup)
                taskForm.findViewById(R.id.radio_group_sharing);
        EditText descriptionView = (EditText)
                taskForm.findViewById(R.id.edit_description);
        EditText summaryView = (EditText)
                taskForm.findViewById(R.id.edit_summary);
        ToggleButton text_toggle = (ToggleButton)
                taskForm.findViewById(R.id.toggle_text);
        ToggleButton photo_toggle = (ToggleButton)
                taskForm.findViewById(R.id.toggle_photo);
        ToggleButton audio_toggle = (ToggleButton)
                taskForm.findViewById(R.id.toggle_audio);


        /* Extract the text fields. */
        String descriptionText = descriptionView.getText().toString();
        String summaryText = summaryView.getText().toString();
        
        /* Now set up the new task with the extracted data. */
        /* TODO: Get rid of this dummy user crud. */
        Task newTask = new Task(DummyTasks.DUMMY_USER);

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
        
        /* Add requests. */
        if (text_toggle.isChecked()) {
            newTask.addRequest(new Request(MediaType.TEXT));
        }
        if (photo_toggle.isChecked()) {
            newTask.addRequest(new Request(MediaType.PHOTO));
        }
        if (audio_toggle.isChecked()) {
            newTask.addRequest(new Request(MediaType.AUDIO));
        }
        
        
        /* TODO: MAKE THIS WORK WITH THE TASK COLLECTIONL INTERFACE. */
        DummyTasks.addItem(newTask);
        
        Toast.makeText(getActivity(), "Task created.", Toast.LENGTH_SHORT).show();
        
    }
    

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.activity_define_task, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        View rootView = inflater
                .inflate(R.layout.fragment_define_task, container, false);

        return rootView;
    }
    
    
    /** Handle the menu button to create entries. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_done:
                 onTaskCreated();
                 /* TODO: Figure out how to navigate away from this fragment... */
                 //NavUtils.???
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
