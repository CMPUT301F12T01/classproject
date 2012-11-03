package ca.ualberta.cs.c301f12t01.gui;

import java.util.UUID;

import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.R.id;
import ca.ualberta.cs.c301f12t01.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;

public class TaskListActivity extends Activity
        implements TaskListFragment.Callbacks {

    /** @deprecated Please find a better way to notify that the screen has two panes. Somehow. */
    private boolean hasTwoPanes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        if (findViewById(R.id.task_detail_container) != null) {
            
            hasTwoPanes = true;
            
            ((TaskListFragment) getFragmentManager()
                    .findFragmentById(R.id.task_list))
                    .setActivateOnItemClick(true);
        }
    }

    public void onItemSelected(UUID taskId) {
        if (hasTwoPanes) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(TaskDetailFragment.ARG_TASK_ID, taskId);
            TaskDetailFragment fragment = new TaskDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.task_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, TaskDetailActivity.class);
            detailIntent.putExtra(TaskDetailFragment.ARG_TASK_ID, taskId);
            startActivity(detailIntent);
        }
    }
}
