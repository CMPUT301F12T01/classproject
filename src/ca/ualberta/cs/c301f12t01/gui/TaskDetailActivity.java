package ca.ualberta.cs.c301f12t01.gui;

import ca.ualberta.cs.c301f12t01.R;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class TaskDetailActivity extends Activity {

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
            NavUtils.navigateUpTo(this, new Intent(this, TaskListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
