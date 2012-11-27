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

import com.google.gson.Gson;

import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * 
 * @author home
 * 
 */
public class TaskServerRetrieval {

	public static ArrayList<Task> getAllTasks() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		// first make the call to the server
		List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
		nvp.add(new BasicNameValuePair("action", "list"));
		Server server = new Server();
		String jsonString = server.post(nvp);
		System.out.println("Server json is " + jsonString);
		// Now convert to an array of server objects
		Gson gson = new Gson();
		ServerObj[] allSO = gson.fromJson(jsonString, ServerObj[].class);
		// iterate through and grab task
		for (ServerObj so : allSO) {
			Task t = getOneTask(so.getId());
			tasks.add(t);
			System.out.println(t.getDescription());
		}
		return tasks;
	}
	
	/**
	 * 
	 * @param id
	 * 			Server ID of the task we want to get
	 * @return
	 * 			task from server 
	 */
	private static Task getOneTask(String id){
		//create our nvp
		List <BasicNameValuePair> nvp = new ArrayList <BasicNameValuePair>();
		nvp.add(new BasicNameValuePair("action", "get"));
		nvp.add(new BasicNameValuePair("id", id));
		//post
		Server server = new Server();
		String jsonString = server.post(nvp);
		//convert to SO
		Gson gson = new Gson();
		ServerObj so = gson.fromJson(jsonString, ServerObj.class);
		return so.getContent();
		
		
	}
}
