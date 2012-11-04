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
 * DefineTaskActivity -- Displays the user interface.
 * 
 * Displays the layout for activiy_define_task.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 *
 */
public class DefineTaskActivity extends Activity {


    /**
     * Starts the {@link DefineTaskFragment}, and configures the home button.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_define_task);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        /* If we're on a large display and the display hasn't been set yet,
         * created the fragment in the display. */
        if (savedInstanceState == null) {
            DefineTaskFragment fragment = new DefineTaskFragment();
            getFragmentManager().beginTransaction()
                .add(R.id.define_task_container, fragment)
                .commit();
        }

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
