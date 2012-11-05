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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.TaskManager;

/**
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public class FulfillTaskFragment extends Fragment {

    public static final String ARG_TASK_ID = "task_id";

    private Task task;

    /**
     * Create a new FulfillTaskFragment for the given Task.
     */
    public FulfillTaskFragment(Task newTask) {
        /* TODO: apparently custom constructors are bad. Fix that linting issue eventually. */
        task = newTask;
    }

    public FulfillTaskFragment() {
        task = null;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Initialize stuff if it hasn't been initialized yet. */
        if (savedInstanceState == null) {
            setHasOptionsMenu(true);

        }
    }

    /**
     * Called when the user decides they are done fulfilling the task.
     * 
     * @returns false is the form was completed incorrectly, else true.
     */
    protected Boolean onFormCompletion() {
        /* TODO: FINISH THIS METHOD! */

        Report report = new Report(task);
        TaskManager.getInstance().addReport(report);
        return true;
    }

    /* TODO: Template some of this stuff away into a superclass. */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_fulfill_task, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fulfill_task,
                container, false);
        
        /* Sets the simple text views. */
        ((TextView) rootView.findViewById(R.id.task_summary))
            .setText(task.getSummary());
        String descriptionText = task.getDescription();
        TextView descripionView = (TextView) rootView.findViewById(R.id.task_description);
        if (descriptionText.equals("")) {
            /* No description? Don't show the text widget at all! */
            descripionView.setVisibility(View.GONE);
        } else {
            descripionView.setText(descriptionText);
        }
        
        /* TODO: code to show each request. */

        return rootView;
    }

    /** Handle the menu button to create entries. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_done:
            onFormCompletion();
            /* TODO: Figure out how to navigate away from this fragment... */
            // NavUtils.???
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}
