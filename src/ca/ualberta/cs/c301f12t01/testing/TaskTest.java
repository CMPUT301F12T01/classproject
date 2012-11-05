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
package ca.ualberta.cs.c301f12t01.testing;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;


/**
 * TaskTest -- This is a test to make sure tasks are created and its methods work.
 * 
 * @author padlesky
 */
public class TaskTest {

	/**
	 * Test to see if a task is created.
	 */
	@Test
	public void creatingTask() {
		Task task = new Task(UUID.randomUUID());
		assertNotNull(task);
	}

	/**
	 * Test to see if adding a request works.
	 */
	@Test
	public void addRequest() {
		Task task = new Task(UUID.randomUUID());
		Request request = new Request(MediaType.TEXT);
		assertTrue(task.addRequest(request));
	}
	
	/**
	 * Test to see if removing a request works.
	 */
	@Test
	public void removeRequest() {
		Task task = new Task(UUID.randomUUID());
		Request request = new Request(MediaType.TEXT);
		task.addRequest(request);
		assertTrue(task.removeRequest(request));
	}
	
	/**
	 * Test to see given a position you can grab a request.
	 */
	@Test
	public void getRequest() {
		Task task = new Task(UUID.randomUUID());
		Request request = new Request(MediaType.TEXT);
		task.addRequest(request);
		assertTrue(task.getRequest(0).equals(request));
	}
	
	/**
	 * Test to see if you can get the total ammount of requests.
	 */
	@Test
	public void requestcount() {
		Task task = new Task(UUID.randomUUID());
		Request request = new Request(MediaType.TEXT);
		task.addRequest(request);
		assertTrue(task.requestCount() == 1);
	}
	
	/**
	 * Test to see if you can set and get the summary.
	 */
	@Test
	public void setGetSummary() {
		Task task = new Task(UUID.randomUUID());
		String summary = "This is a summary for this task.";
		task.setSummary(summary);
		assertTrue(task.getSummary().matches(summary));
	}
	
	/**
	 * Test to see if you can se and get the description,
	 */
	@Test
	public void setGetDescription() {
		Task task = new Task(UUID.randomUUID());
		String description = "This is a lengthy description for the given task that will be implemented eventually but this is only a test as you see.";
		task.setDescription(description);
		assertTrue(task.getDescription().matches(description));
	}

}
