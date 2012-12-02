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
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import ca.ualberta.cs.c301f12t01.common.Report;

/**
 * ReportListFragment - in charge of displaying the list of reports
 * 
 * @author Bronte Lee <bronte@ualberta.ca>
 *
 */
public class ReportListFragment extends ListFragment implements Observer {


    public static final String ARG_TASK_ID = "task_id"; 
    
	private static final String STATE_ACTIVATED_POSITION = "activated_position";
	private Callbacks callbacks = doNothingCallbacks;
	private int activatedPosition = ListView.INVALID_POSITION;
	
	/* A list of all the reports for the task */
	private List<Report> taskReports = null;
	

	public interface Callbacks {
		public void onItemSelected(UUID reportId);
	}
    
	private static Callbacks doNothingCallbacks = new Callbacks() {
		public void onItemSelected(UUID reportId) {
		}
	};
    
	public ReportListFragment() {
	}

	/** onCreate - obtain information from its activity and setListAdapter
	 * 
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UUID taskId = null;
		
		/* Obtain the task ID that was passed to it */
		if (getArguments().containsKey(ARG_TASK_ID)) {
			taskId = (UUID) getArguments()
					.getSerializable(ARG_TASK_ID);
		}
		
		taskReports = TaskSourceApplication.getReportsForTask(taskId); 
		setListAdapter( new ReportAdapter(getActivity(), taskReports));
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState != null && savedInstanceState
				.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}

		callbacks = (Callbacks) activity;
		android.util.Log.d("Act-LIFECYCLE", "ReportListFragment - onAttach");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		callbacks = doNothingCallbacks;
		android.util.Log.d("Act-LIFECYCLE", "ReportListFragment - onDetach");
	}

	/* If a report is selected, display its responses */
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);

		/* Get report position */
		callbacks.onItemSelected(taskReports.get(position).getId());
		
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

	/* C&P */
	public void update(Observable observable, Object ignored) {
		((BaseAdapter) getListAdapter()).notifyDataSetChanged();
	}
	
}
