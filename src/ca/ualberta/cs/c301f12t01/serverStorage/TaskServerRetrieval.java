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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * 
 * @author home
 * 
 */
public class TaskServerRetrieval {

	/**
	 * 
	 * @return
	 * 			Returns all simple server objects on server
	 */
	public static ServerObj[] getAllSO() {
		// first make the call to the server
		List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
		nvp.add(new BasicNameValuePair("action", "list"));
		Server server = new Server();
		String jsonString = server.post(nvp);
		// Now convert to an array of server objects
		Gson gson = new Gson();
		ServerObj[] allSO = gson.fromJson(jsonString, ServerObj[].class);
		return allSO;
	}

	/**
	 * 
	 * @return
	 * 			all tasks on the server
	 */
	public static ArrayList<Task> getAllTasks() {
		ServerObj[] allSO = getAllSO();
		ArrayList<Task> tasks = new ArrayList<Task>();
		// iterate through and grab task
		for (ServerObj so : allSO) {
			if (so.getSummary().equals("task")) {
				Task t = getContentFromServer(so.getId());
				tasks.add(t);
			}
		}
		return tasks;
	}
	
	public static HashMap<UUID, Task> getUserTasks(UUID userid){
		ArrayList<Task> al = getAllTasks();
		HashMap<UUID, Task> own = new HashMap<UUID, Task>();
		for (Task t : al){
			if (t.getUser().equals(userid)){
				own.put(t.getId(), t);
			}
		}
		return own;
	}
	
	public static HashMap<UUID, Task> getGlobalTasks(){
		ArrayList<Task> al = getAllTasks();
		HashMap<UUID, Task> own = new HashMap<UUID, Task>();
		for (Task t : al){
			if (t.isGlobal()){
				own.put(t.getId(), t);
			}
		}
		return own;
	}
	
	public static HashMap<UUID, Task> getLocalTasks(){
		ArrayList<Task> al = getAllTasks();
		HashMap<UUID, Task> own = new HashMap<UUID, Task>();
		for (Task t : al){
			if (t.isLocal()){
				own.put(t.getId(), t);
			}
		}
		return own;
	}

	/**
	 * Method works to get reports and tasks
	 * 
	 * @param id
	 *            Server ID of the content we want to get
	 * @return content from server
	 */
	public static Task getContentFromServer(String id) {
		// create our nvp
		List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
		nvp.add(new BasicNameValuePair("action", "get"));
		nvp.add(new BasicNameValuePair("id", id));
		// post
		Server server = new Server();
		String jsonString = server.post(nvp);
		// convert to SO
		Gson gson = new Gson();
		ServerObj so = gson.fromJson(jsonString, ServerObj.class);
		return so.getContent();
	}

	public static ServerObj getTaskSO(UUID id) {
		// first get all tasks
		ServerObj[] allSO = getAllSO();
		for (ServerObj so : allSO) {
			so.getContentFromServer(); //must call this everytime
			if (so.getSummary().equals("task")) {
				if (so.getContent().getId().equals(id)) {
					return so;
				}
			}
		}
		System.out.println("Could not find Server Object");
		return null; // null if we can't find it
	}
}
