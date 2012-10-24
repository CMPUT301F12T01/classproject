package ca.ualberta.cs.c301f12t01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class taskDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(taskDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(taskDetailFragment.ARG_ITEM_ID));
            taskDetailFragment fragment = new taskDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.task_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, taskListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
