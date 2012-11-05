package ca.ualberta.cs.c301f12t01.gui;

import java.util.UUID;

import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.dummy.DummyTasks;
import android.os.Bundle;
import android.view.MenuItem;
import android.app.Activity;
import android.support.v4.app.NavUtils;

public class FulfillTaskActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill_task);

        /* We need to know the Task ID in order to fulfill a task! */
        UUID taskId = (UUID) getIntent().getSerializableExtra(
                FulfillTaskFragment.ARG_TASK_ID);
        
        /* TODO: use TaskManager. */
        Task task = DummyTasks.ITEM_MAP.get(taskId);

        /* Start the FulfillTaskFragment with the appropriate parameters. */
        FulfillTaskFragment frag = new FulfillTaskFragment(task);
        getFragmentManager().beginTransaction()
            .add(R.id.fulfill_task_fragment_container, frag)
            .commit();

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
