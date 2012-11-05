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

import java.util.Iterator;
import java.util.UUID;

import org.junit.Test;

import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.TaskManager;

/**
 * Contains test for testing the TaskManager class * 
 * @author home
 */
public class TaskManagerTest {

	@Test
	public void add_global_task() {
		TaskManager tm = TaskManager.getInstance();
		Task t1 = TestUtils.makeSimpleTask();
		tm.addTask(t1); //now we should have one global task
		Iterator<Task> iter = tm.getGlobalTasks();
		Task t2 = iter.next();
		assertTrue(t1.equals(t2));
	}
	
	@Test
	public void add_local_task() {
		TaskManager tm = TaskManager.getInstance();
		Task t1 = TestUtils.makeSimpleTask();
		t1.setLocal();
		tm.addTask(t1); //now we should have one local task
		Iterator<Task> iter = tm.getLocalTasks();
		Task t2 = iter.next();
		assertTrue(t1.equals(t2));		
	}
	
	@Test
	public void get_global_id(){
		TaskManager tm = TaskManager.getInstance();
		Task t1 = TestUtils.makeSimpleTask();
		tm.addTask(t1);
		Task t2 = tm.get(t1.getId());
		assertTrue(t1.equals(t2));		
	}
	
	@Test
	public void get_local_id(){
		TaskManager tm = TaskManager.getInstance();
		Task t1 = TestUtils.makeSimpleTask();
		t1.setLocal();
		tm.addTask(t1);
		Task t2 = tm.get(t1.getId());
		assertTrue(t1.equals(t2));		
	}
	
	@Test
	public void get_invalid_id(){
		TaskManager tm = TaskManager.getInstance();
		Task t1 = TestUtils.makeSimpleTask();
		t1.setLocal();
		tm.addTask(t1);
		Task t2 = tm.get(UUID.randomUUID());
		assertTrue(t2 == null);		
	}
}
