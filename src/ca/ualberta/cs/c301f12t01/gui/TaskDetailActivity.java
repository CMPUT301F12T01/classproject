package ca.ualberta.cs.c301f12t01.gui;

import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.R.id;
import ca.ualberta.cs.c301f12t01.R.layout;
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
            Bundle arguments = new Bundle();
            arguments.putString(TaskDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(TaskDetailFragment.ARG_ITEM_ID));
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
