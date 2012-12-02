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

import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * Class to remove tasks from the server
 * 
 * @author home
 * 
 */
public class TaskServerRemove {

	/**
	 * Takes a task and removes it from the server
	 * @param task
	 * 			Task to be removed
	 */
	public static void remove(Task task) {
		// get SO based off of taskID
		TaskServerObj so = TaskServerRetrieval.getTaskSO(task.getId());
		String soid = so.getId();
		// Build our get args
		List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
		nvp.add(new BasicNameValuePair("action", "remove"));
		nvp.add(new BasicNameValuePair("id", soid));
		// tell server
		Server server = new Server();
		server.post(nvp);

	}
}
