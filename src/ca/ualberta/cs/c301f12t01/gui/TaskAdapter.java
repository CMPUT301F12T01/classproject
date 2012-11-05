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

import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TaskAdapter -- 
 * 
 * @author padlesky
 *
 */
public class TaskAdapter extends BaseAdapter{
	
	LayoutInflater taskListInflater;
	Context currentContext;
	List<Task> taskCollection;
	
	/**
	 * 
	 */
	public TaskAdapter(Context context, List<Task> taskToUseForever) {
		currentContext = context;
		taskCollection = taskToUseForever;
		taskListInflater = (LayoutInflater) currentContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount() {
		return taskCollection.size();
	}
	
	public Object getItem(int position) {
		return taskCollection.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View vi;
		
		if (convertView == null) {
			vi = taskListInflater.inflate(R.layout.fragment_task_list_summary, null);
		} else {
			vi = convertView;
		}
		
		Task task = (Task) getItem(position);
		
		TextView summary = (TextView) vi.findViewById(R.id.text_task_summary);
		ImageView responseIndicator = (ImageView) vi.findViewById(R.id.image_status_indicator);
		
		summary.setText(task.getSummary());
		
		//0 means Visible, 1 means Invisible, 2 means gone
		responseIndicator.setVisibility(1);
		
		return vi;
	}
}
