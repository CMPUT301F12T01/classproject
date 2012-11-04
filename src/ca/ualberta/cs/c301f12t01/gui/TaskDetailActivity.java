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
import android.view.MenuItem;
import ca.ualberta.cs.c301f12t01.R;

/**
 * TaskDetailActivity -- Displays the user interface for Task Details.
 * 
 * Displays the layout for activity_task_detail, which displays a fragment of TaskDetailFragment 
 * {@link TaskDetailFragment} that handles the displaying of the task details.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 *
 */
public class TaskDetailActivity extends Activity {

	/**
	 * Starts the {@link TaskListFragment}.
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            /* TODO: Template method this shiz, yo! */
            Bundle arguments = new Bundle();
            arguments.putSerializable(TaskDetailFragment.ARG_TASK_ID,
                    getIntent().getSerializableExtra(TaskDetailFragment.ARG_TASK_ID));
            TaskDetailFragment fragment = new TaskDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.task_detail_container, fragment)
                    .commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
