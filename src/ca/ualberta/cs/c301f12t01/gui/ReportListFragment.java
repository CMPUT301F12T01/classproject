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

import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.ReportManager;
import ca.ualberta.cs.c301f12t01.model.TaskManager;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author Bronte Lee
 *
 */
public class ReportListFragment extends ListFragment implements Observer {


    public static final String ARG_TASK_ID = "task_id";
    
    private Task task;
	
    
    // C&P: A bunch of callback stuff from TaskListFragments 
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	private Callbacks callbacks = doNothingCallbacks;
	private int activatedPosition = ListView.INVALID_POSITION;

	public interface Callbacks {
		public void onItemSelected(UUID reportId);
	}
    
	private static Callbacks doNothingCallbacks = new Callbacks() {
		public void onItemSelected(UUID reportId) {
		}
	};
    
	public ReportListFragment() {
	}

	/* More C&P: this time from TaskLikstFrag AND TaskDetailFrag */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TaskManager tm = ((TaskSourceApplication) getActivity().getApplication()).getTaskManager();
		ReportManager rm = ((TaskSourceApplication) getActivity().getApplication()).getReportManager();

		/*
		 * C&P from TaskDetailFragment 
		 * 
		 * Obtains the task ID that was passed to it
		 * 
		 */
		if (getArguments().containsKey(ARG_TASK_ID)) {
			UUID taskId = (UUID) getArguments()
					.getSerializable(ARG_TASK_ID);
			task = tm.get(taskId);
		}

		setListAdapter(new ReportAdapter(getActivity(), rm.getReports(task)));
		
		/* Proving Toast statements */
		Toast.makeText(getActivity(), "TaskID: " + task.getId().toString(), 
				Toast.LENGTH_SHORT).show();
		
		Toast.makeText(getActivity(), "Number of Reports: " + 
				rm.getReports(task).size(), 
				Toast.LENGTH_SHORT).show();
		
		
	}


	/* C&P */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState != null && savedInstanceState
				.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
		}
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_report_list , menu);
	}

	/* More C&P related to callbacks */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}

		callbacks = (Callbacks) activity;
	}

	/* C&P */
	@Override
	public void onDetach() {
		super.onDetach();
		callbacks = doNothingCallbacks;
	}

	/* C&P */
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);

		/* Need to swap to getting reports */
		ReportManager rm = ((TaskSourceApplication) getActivity().getApplication()).getReportManager();
		List<Report> list = rm.getReports(task);
		callbacks.onItemSelected(list.get(position).getId());

	}

	/* C&P */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (activatedPosition != ListView.INVALID_POSITION) {
			outState.putInt(STATE_ACTIVATED_POSITION, activatedPosition);
		}
	}

	/* C&P */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		getListView().setChoiceMode(activateOnItemClick
				? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	/* C&P */
	public void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(activatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		activatedPosition = position;
	}

	/* C&P */
	public void update(Observable observable, Object ignored) {

		((BaseAdapter) getListAdapter()).notifyDataSetChanged();
	}

}
