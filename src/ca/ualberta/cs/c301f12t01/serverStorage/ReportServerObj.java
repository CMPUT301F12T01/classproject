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
package ca.ualberta.cs.c301f12t01.serverStorage;

import ca.ualberta.cs.c301f12t01.common.Report;

/**
 * Object that is stored on the server Used for converting server json to Report
 * Note that Report can be retrieved with getContent
 * 
 * @author home
 * 
 */
public class ReportServerObj {

	// This is how things are stored on the server
	private String summary;
	private Report content;
	private String id;
	private String description;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Report getContent() {
		return content;
	}

	public void setContent(Report content) {
		this.content = content;
	}
	/**
	 * This needs to be called to get the actual content
	 * with a get action
	 */
	public void getContentFromServer(){
		Report r = ReportServerRetrieval.getContentFromServer(this.id);
		setContent(r);
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
