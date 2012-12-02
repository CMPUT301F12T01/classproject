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

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;

import com.google.gson.Gson;

/**
 * 
 * @author home
 * 
 */
public class ReportServerStorage {

	/**
	 * 
	 * @param report
	 * 			Report to store on the server
	 */
	public static void storeReport(Report report) {
		// first make report into json
		Gson gson = new Gson();
		String reportJson = gson.toJson(report);
		// Build our get args
		List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
		nvp.add(new BasicNameValuePair("action", "post"));
		nvp.add(new BasicNameValuePair("summary", "report"));
		nvp.add(new BasicNameValuePair("content", reportJson));
		// tell server
		Server server = new Server();
		server.post(nvp);

	}
}
