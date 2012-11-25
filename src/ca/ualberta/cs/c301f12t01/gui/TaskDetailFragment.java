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
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.dummy.DummyTasks;
import ca.ualberta.cs.c301f12t01.model.TaskManager;

/**
 * TaskDetailFragment -- Displays the details about a task.
 * 
 * Fragment that displays details about a Task. Uses the `fragment_task_detail`
 * for displaying an actual fragment, or if no fragment was specified, uses
 * `fragment_no_task_detail` for displaying the "sorry, there ain't no task to
 * display" screen. layout.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public class TaskDetailFragment extends Fragment {

    /* TODO: Get some more global place to store the following string. */
    public static final String ARG_TASK_ID = "task_id";

    private Task task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {

            setHasOptionsMenu(true);

            /*
             * Hopefully this fragment was initialized with some Task ID. Try to
             * retrieve the task ID and retrieve the Task in order to display it
             * later.
             */
            if (getArguments().containsKey(ARG_TASK_ID)) {
                UUID taskId = (UUID) getArguments()
                        .getSerializable(ARG_TASK_ID);
                
                /* TODO: REMOVE DUMMY */
                //TaskManager tm = ((TaskSourceApplication) getActivity().getApplication()).getTaskManager();
                //task = tm.get(taskId);
                task = DummyTasks.ITEM_MAP.get(taskId);


            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        int layout_id;
        View rootView;

        /* TODO: Make some sort of template pattern here or something. */

        /* Inflate the root view depending on whether we have a Task or not. */
        layout_id = (task == null) ? R.layout.fragment_no_task_detail /*
                                                                       * No
                                                                       * task?
                                                                       * No
                                                                       * detail.
                                                                       */
        : R.layout.fragment_task_detail; /* Else, get the details. */
        rootView = inflater.inflate(layout_id, container, false);

        /* Invoke the proper method. */
        switch (layout_id) {
        case R.layout.fragment_no_task_detail:
            completeNoDetailViewInflation(rootView);
            break;
        case R.layout.fragment_task_detail:
            completeTaskDetailViewInflation(rootView);
            break;
        }

        return rootView;
    }

    /**
     * When the user wants to view reports, start the report viewing activity.
     */
    private void onUserSelectViewReports() {
        startActivityWithTask(ReportListActivity.class);
    }

    /**
     * When the user wants to fulfill reports, start the task fulfillment
     * activity.
     */
    private void onUserSelectFulfill() {
        startActivityWithTask(FulfillTaskActivity.class);
    }

    /* TODO: This should really not be in *this* class. */
    @SuppressWarnings("rawtypes")
    private void startActivityWithTask(Class activity) {
        Intent activityIntent = new Intent(getActivity(), activity);

        activityIntent.putExtra(ARG_TASK_ID, task.getId());

        startActivity(activityIntent);
    }

    /**
     * Given an inflated `fragment_task_detail` view, fills it out with the
     * current task.
     * 
     * @return the completed task detail view
     */
    protected View completeTaskDetailViewInflation(View rootView) {
        /* Get the associated views. */
        TextView descriptionView = (TextView) rootView
                .findViewById(R.id.task_description);
        TextView summaryView = (TextView) rootView
                .findViewById(R.id.task_summary);
        TextView requirementView = (TextView) rootView
                .findViewById(R.id.task_requires);
        TextView sharingView = (TextView) rootView
                .findViewById(R.id.task_sharing_setting);

        /* Collect strings and stuff from the Task instance. */

        String descriptionText = task.getDescription();
        String summaryText = task.getSummary();

        /* TODO: Get rid of this ugly mess! */
        String requirementText;
        Set<String> requirementSet = new HashSet<String>();

        /* Setup the requirement text. */
        for (Request request : task) {
            requirementSet.add(request.getType().toString());
        }

        /* LOOK AWAY! LOOK AWAY! */
        StringBuilder requirementBuilder = new StringBuilder();
        Iterator<String> iter = requirementSet.iterator();
        while (iter.hasNext()) {
            requirementBuilder.append(iter.next());
            if (!iter.hasNext()) {
                break;
            }
            requirementBuilder.append(" ");
        }
        requirementText = requirementBuilder.toString();

        /* It's over. The horror is over. There's nothing to see here, kids. */

        /* Set the associated views. */
        summaryView.setText(summaryText);

        /* Set the description text. */
        if (descriptionText.equals("")) {
            /*
             * Description is empty. State that there is no description instead
             * of giving the user a blank screen.
             */
            descriptionView.setText("No description");
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

        return descriptionView;
    }

    /**
     * Given an inflated `fragment_task_detail` view, fills it out with the
     * current task.
     * 
     * @return the completed task detail view
     */
    protected View completeNoDetailViewInflation(View rootView) {
        /* The view does not need to be filled. */
        return rootView;

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Fragment#onCreateOptionsMenu(android.view.Menu,
     * android.view.MenuInflater)
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_task_detail, menu);
    }

    /** Handle the menu button to create entries. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_fulfill_task:
            //onUserSelectFulfill();
        	Toast.makeText(getActivity(), "Turned off for now!", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.menu_view_reports:
            //onUserSelectViewReports();
        	Toast.makeText(getActivity(), "Turned off for now!", Toast.LENGTH_SHORT).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}
