package ca.ualberta.cs.c301f12t01.gui;

import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.R.id;
import ca.ualberta.cs.c301f12t01.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;

public class TaskListActivity extends Activity
        implements TaskListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        if (findViewById(R.id.task_detail_container) != null) {
            mTwoPane = true;
            ((TaskListFragment) getFragmentManager()
                    .findFragmentById(R.id.task_list))
                    .setActivateOnItemClick(true);
        }
    }

    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(TaskDetailFragment.ARG_ITEM_ID, id);
            TaskDetailFragment fragment = new TaskDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.task_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, TaskDetailActivity.class);
            detailIntent.putExtra(TaskDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
