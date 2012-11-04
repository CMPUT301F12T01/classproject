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

import java.io.Serializable;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.app.Activity;
import android.app.Fragment;

public class TaskListActivity extends Activity
        implements TaskListFragment.Callbacks {

    /** @deprecated Please find a better way to denote that the screen has two panes. Somehow. */
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
    
    
    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
            case R.id.menu_define_task:
                onClickDefineTask(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    /**
     * A shortcut for {@link startNewFragment}, but without any key/Serializable schenanigans.
     * @param fragmentClass
     * @param fragmentActivityClass
     */
    @SuppressWarnings("rawtypes")
    protected void startNewFragment(Class fragmentClass, Class fragmentActivityClass) {
        startNewFragment(fragmentClass, fragmentActivityClass, null, null);
    }
    
    /**
     * Starts a new fragment in the appropriate place. If the screen has one
     * pane (as with a mobile phone), an intent is created with the given
     * FragmentActivity. Else, if the screen has two panes (such as with a large
     * tablet), the given Fragment is started in the detail pane.
     * 
     * Some fragments require some extra information. This is usually one
     * key/value pair. You may provide the String key and an arbitrary
     * Serializable value. If this behavior is not required, pass in
     * <code>null</code> for the key. One may also use the two-argument method
     * of the same name instead.
     * 
     */
    @SuppressWarnings("rawtypes")
    protected void startNewFragment(Class fragmentClass, Class fragmentActivityClass, String key, Serializable arbitraryValue) {
        if (hasTwoPanes) {
            Bundle arguments = new Bundle();
            
            /* If the user wants an extra value to tag along... */
            if (key != null) {
                arguments.putSerializable(key, arbitraryValue);
            }
            
            Fragment fragment;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(arguments);
                getFragmentManager().beginTransaction()
                        .replace(R.id.task_detail_container, fragment)
                        .commit();
            } catch (InstantiationException e) {
                /* I DON'T KNOW WHAT TO DO HERE. */
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                /* OR HERE. */
                e.printStackTrace();
            }

        } else {
            Intent detailIntent = new Intent(this, fragmentActivityClass);
            
            if (key != null) {
                detailIntent.putExtra(key, arbitraryValue);
            }
            
            startActivity(detailIntent);
        }
    }

    public void onItemSelected(UUID taskId) {
        startNewFragment(TaskDetailFragment.class, TaskDetailActivity.class,
                TaskDetailFragment.ARG_TASK_ID, taskId);
    }
    
    /** Starts the "define new task" screen. */
    public void onClickDefineTask(MenuItem item) {
        startNewFragment(DefineTaskFragment.class, DefineTaskActivity.class);

    }
}
