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
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.ReportManager;
import ca.ualberta.cs.c301f12t01.model.TaskManager;
import ca.ualberta.cs.c301f12t01.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TaskAdapter -- Creates a new view from tasks suitable for a ListView.
 * 
 * 
 * 
 * @author padlesky
 *
 */
public class TaskAdapter extends BaseAdapter{
	
	LayoutInflater taskListInflater;
	Context currentContext;
	List<Task> taskList;
	
	/**
	 * Creates new TaskAdapter
	 * 
	 * @param context
	 * 				An Android context (Activity, Application, etc.) that will be
	 * 				used for things like resource acquisition and doing locale-
	 * 				specific actions.
	 * @param taskToUseForever
	 * 				The specific task instance that this will adapt.
	 */
	public TaskAdapter(Context context, List<Task> taskToUseForever) {
		currentContext = context;
		taskList = taskToUseForever;
		taskListInflater = (LayoutInflater) currentContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/**
	 * 
	 * @return Returns how many tasks there are.
	 */
	public int getCount() {
		return taskList.size();
	}
	
	/**
	 * Given a position it will return the Task at that position.
	 * @return Returns a Task.
	 */
	public Object getItem(int position) {
		return taskList.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// View either given to us from convert view, or we might have to inflate a new layout
		View vi;
		
		// Recycle the convertView, if possible, else create a new view
		if (convertView == null) {
			vi = taskListInflater.inflate(R.layout.fragment_task_list_summary, null);
		} else {
			vi = convertView;
		}
		
		// Grab the specified Task from our List of tasks.
		Task task = (Task) getItem(position);
		
		// Grabs all of the fields of the template.
		TextView summary = (TextView) vi.findViewById(R.id.text_task_summary);
		ImageView responseIndicator = (ImageView) vi.findViewById(R.id.image_status_indicator);
		
		// Sets all values in the new view.
		summary.setText(task.getSummary());

		List<Report> reports = ReportManager.getInstance().getReports(task.getId());
		// Sets marker to visible if a report has been made to a task.
		if (reports.size() != 0) {
			responseIndicator.setVisibility(0);
		}
		
		return vi;
	}
}
