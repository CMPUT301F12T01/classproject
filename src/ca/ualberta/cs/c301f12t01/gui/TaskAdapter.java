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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.ReportManager;
import ca.ualberta.cs.c301f12t01.model.TaskCollection;

/**
 * TaskAdapter -- Creates views for Tasks from a given TaskCollection. These
 * views are suitable for insertion into a ListView.
 * 
 * @author Aaron Padlesky <padlesky>
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * 
 */
public class TaskAdapter extends BaseAdapter {

    LayoutInflater taskListInflater;
    Context currentContext;
    TaskCollection collection;

    /**
     * Creates new TaskAdapter
     * 
     * @param context
     *            An Android context (Activity, Application, etc.) that will be
     *            used for things like resource acquisition and doing locale-
     *            specific actions.
     * @param taskCollection
     *            The specific task instance that this will adapt.
     */
    public TaskAdapter(Context context, TaskCollection taskCollection) {
        currentContext = context;
        collection = taskCollection;
        taskListInflater = (LayoutInflater) currentContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /** Returns the number of tasks in the collection. */
    public int getCount() {
        return collection.size();
    }

    /**
     * Given a position, returns the associated Task.
     * 
     * @return Returns a Task.
     */
    public Task getItem(int position) {
        return collection.getAt(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /** Gets a view for ONE task. */
    public View getView(int position, View oldView, ViewGroup parent) {

        // View either given to us from convertView, or we might have to
        // inflate a new layout
        View constructedView;

        // Recycle the convertView, if possible, else create a new view
        if (oldView != null) {
            constructedView = oldView;
        } else {
            constructedView = taskListInflater.inflate(
                    R.layout.fragment_task_list_summary, null);
        }

        // Grab the specified Task from our List of tasks.
        Task task = getItem(position);

        // Grabs all of the fields of the template.
        TextView summary = (TextView) constructedView
                .findViewById(R.id.text_task_summary);
        ImageView responseIndicator = (ImageView) constructedView
                .findViewById(R.id.image_status_indicator);

        // Sets the main text to be the summary.
        summary.setText(task.getSummary());

        /* TODO: Get reports to not crash on start-up... */
        /*
        boolean hasReports = ReportManager.getInstance().taskHasReports(task);
        // Hides the marker if no reports have been made.
        if (!hasReports) {
            responseIndicator.setVisibility(View.INVISIBLE);
        }
        */

        return constructedView;
    }
}
