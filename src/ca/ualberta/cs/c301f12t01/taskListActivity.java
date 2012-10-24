package ca.ualberta.cs.c301f12t01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class taskListActivity extends FragmentActivity
        implements taskListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        if (findViewById(R.id.task_detail_container) != null) {
            mTwoPane = true;
            ((taskListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.task_list))
                    .setActivateOnItemClick(true);
        }
    }

    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(taskDetailFragment.ARG_ITEM_ID, id);
            taskDetailFragment fragment = new taskDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.task_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, taskDetailActivity.class);
            detailIntent.putExtra(taskDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
