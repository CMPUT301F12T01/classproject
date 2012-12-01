package ca.ualberta.cs.c301f12t01.gui.helper;

import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.R.layout;
import ca.ualberta.cs.c301f12t01.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class TakePhotoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
