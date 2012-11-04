package ca.ualberta.cs.c301f12t01.gui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import ca.ualberta.cs.c301f12t01.R;

public class DefineTaskActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_define_task);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        DefineTaskFragment fragment = new DefineTaskFragment();
        getFragmentManager().beginTransaction()
            .add(R.id.define_task_container, fragment)
            .commit();

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
