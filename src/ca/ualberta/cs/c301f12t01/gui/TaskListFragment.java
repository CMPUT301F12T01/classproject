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

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.ReportManager;
import ca.ualberta.cs.c301f12t01.model.TaskManager;

/**
 * 
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 *
 */
public class TaskListFragment extends ListFragment implements Observer {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks callbacks = doNothingCallbacks;
    private int activatedPosition = ListView.INVALID_POSITION;

    public interface Callbacks {

        public void onItemSelected(UUID taskId);
    }

    private static Callbacks doNothingCallbacks = new Callbacks() {
        public void onItemSelected(UUID taskId) {
        }
    };

    public TaskListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TaskManager tm = ((TaskSourceApplication) getActivity().getApplication()).getTaskManager();
        /**TODO Somewhere we have to initialize Report Manager as well. Imma put it here for now */
        ReportManager rm = ((TaskSourceApplication) getActivity().getApplication()).getReportManager();

        /*TODO Take into account global tasks maybe?*/
        setListAdapter(new TaskAdapter(getActivity(),
                tm.getLocalTasks()));

        
        /* Add action bar options, because they are super cool. */
        setHasOptionsMenu(true);

        /* Action bar config! */
        //ActionBar menubar = getActivity().getActionBar();
        //menubar.setDisplayShowTitleEnabled(false);
        
        android.util.Log.d("Frag-LIFECYCLE", "TaskListFragment-onCreate");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState
                .containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
      
        android.util.Log.d("Frag-LIFECYCLE", "TaskListFragment-onViewCreate");
    }
    

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.activity_task_list , menu);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        callbacks = (Callbacks) activity;
        
        android.util.Log.d("Frag-LIFECYCLE", "TaskListFragment-onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = doNothingCallbacks;
        android.util.Log.d("Frag-LIFECYCLE", "TaskListFragment-onDetach");
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        TaskManager tm = ((TaskSourceApplication) getActivity().getApplication()).getTaskManager();
        List<Task> list = tm.getLocalTasks();
        callbacks.onItemSelected(list.get(position).getId());
        /*TODO This also needs to handle global tasks*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (activatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, activatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(activatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }
 
        activatedPosition = position;
    }

    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable observable, Object ignored) {
        
        ((BaseAdapter) getListAdapter()).notifyDataSetChanged();
    }
}
