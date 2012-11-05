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
 * ReportAdapter -- 
 * 
 * @author padlesky
 *
 */
public class ReportAdapter extends BaseAdapter{
	
	LayoutInflater taskListInflater;
	Context currentContext;
	List<Report> reportList;
	
	
	/**
	 * 
	 */
	public ReportAdapter(Context context, List<Report> reportToUseForever) {
		currentContext = context;
		reportList = reportToUseForever;
		taskListInflater = (LayoutInflater) currentContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount() {
		return reportList.size();
	}
	
	public Object getItem(int position) {
		return reportList.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View vi;
		
		if (convertView == null) {
			vi = taskListInflater.inflate(R.layout.fragment_report_list_summary, null);
		} else {
			vi = convertView;
		}
		
		Report report = (Report) getItem(position);
		
		TextView mediaType = (TextView) vi.findViewById(R.id.text_report_media);
		TextView timestamp = (TextView) vi.findViewById(R.id.text_report_timestamp);
		
		mediaType.setText(report.responseTypes());
		timestamp.setText(report.getTimestamp().toString());
		
		return vi;
	}
}
