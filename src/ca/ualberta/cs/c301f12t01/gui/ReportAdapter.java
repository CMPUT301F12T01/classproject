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
import ca.ualberta.cs.c301f12t01.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ReportAdapter -- Creates a new view from a tasks responses suitable for a ListView
 * 
 * 
 * 
 * @author padlesky
 *
 */
public class ReportAdapter extends BaseAdapter{
	
	LayoutInflater taskListInflater;
	Context currentContext;
	List<Report> reportList;
	
	
	/**
	 * Creates new ReportAdapter
	 * 
	 * @param context
	 * 				An Android context (Activity, Application, etc.) that will be
	 * 				used for things like resource acquisition and doing locale-
	 * 				specific actions.
	 * @param reportToUseForever
	 * 				The specific report instance that this will adapt.
	 */
	public ReportAdapter(Context context, List<Report> reportToUseForever) {
		currentContext = context;
		reportList = reportToUseForever;
		taskListInflater = (LayoutInflater) currentContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/**
	 * 
	 * @return Returns how many reports of a Task.
	 */
	public int getCount() {
		return reportList.size();
	}
	
	/**
	 * Given a position it will return the report from a task at that position.
	 * @return Returns a report.
	 */
	public Object getItem(int position) {
		return reportList.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// View either given to us from convert view, or we might have to inflate a new layout
		View vi;
		
		// Recycle the convertView, if possible, else create a new view
		if (convertView == null) {
			vi = taskListInflater.inflate(R.layout.fragment_report_list_summary, null);
		} else {
			vi = convertView;
		}
		
		// Get the specified entry from our Report
		Report report = (Report) getItem(position);
		
		// Grab all the fields of out template.
		TextView mediaType = (TextView) vi.findViewById(R.id.text_report_media);
		TextView timestamp = (TextView) vi.findViewById(R.id.text_report_timestamp);
		
		// Setting all values in the new view
		mediaType.setText(report.responseTypes());
		timestamp.setText(report.getTimestamp().toString());
		
		return vi;
	}
}
