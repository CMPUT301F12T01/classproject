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
public class TaskServerStorage {
	
	public static void storeTask(Task task){
		
		//first make task into json
		Gson gson = new Gson();
		String taskJson = gson.toJson(task);
		//Build our get args
		List <BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
		nvp.add(new BasicNameValuePair("action", "post"));
		nvp.add(new BasicNameValuePair("summary", "task"));
		nvp.add(new BasicNameValuePair("content", taskJson));	
		//tell server
		Server server = new Server();
		server.put(nvp);
		
	}
}
