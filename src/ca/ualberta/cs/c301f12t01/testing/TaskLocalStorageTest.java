package ca.ualberta.cs.c301f12t01.testing;

import java.util.Collection;
import java.util.Iterator;

import android.test.AndroidTestCase;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.localStorage.DeviceStorage;
import ca.ualberta.cs.c301f12t01.testing.TestUtils;

/**
 * Test class from the DatabaseTesting android project
 * 
 * This class attempts to test all of the functionality
 * of storing and retrieving Tasks and Responses from 
 * the sqlite database
 * 
 * IMPORTANT!!!!!!
 * Because this class is from another project for testing
 * it should not run. It is only here to demonstrate that
 * testing was performed for the database
 * 
 * @author nborle
 *
 */

public class TaskLocalStorageTest extends AndroidTestCase
{

	public void test_store_localtask() 
	{
		Task task = TestUtils.makeSimpleTask();
		task.setLocal();
		DeviceStorage ds = new DeviceStorage(getContext());
		
		ds.storeTask(task);
	}
	
	public void test_retrieve_localtask() 
	{
		Task task = TestUtils.makeSimpleTask();
		task.setLocal();
		DeviceStorage ds = new DeviceStorage(getContext());
		
		ds.storeTask(task);
		Collection<Task> taskList = ds.getLocalTasks();
		assertNotNull(taskList);
		
	}
	
	public void test_retrieve_globaltask() 
	{
		// put in 1 global task
		Task task = TestUtils.makeSimpleTask();
		task.setLocal();
		DeviceStorage ds = new DeviceStorage(getContext());
		
		ds.storeTask(task);
		Collection<Task> taskList = ds.getGlobalTasks();
		assertEquals(1, taskList.size());
		
	}
	
	public void test_retrieve_samelocaltask() 
	{
		Task task = TestUtils.makeSimpleTask();
		task.setLocal();
		DeviceStorage ds = new DeviceStorage(getContext());
		
		ds.storeTask(task);
		Collection<Task> taskList = ds.getLocalTasks();
		Iterator<Task> iter = taskList.iterator();
		
		while (iter.hasNext()){
			Task t = iter.next();
			if (t.getId().toString().equals(task.getId().toString())) {
				assertEquals(task.getId().toString(), t.getId().toString());
			}
		}
	}
	
	public void test_retrieve_requests() 
	{
		Task task = TestUtils.makeSimpleTask();
		task.setLocal();
		//ADDED BY MITCHELL
		Request r = TestUtils.makeSimpleRequest();
		r.setDescription("I've got a new description");
		task.addRequest(r);
		DeviceStorage ds = new DeviceStorage(getContext());
		
		ds.storeTask(task);
		Collection<Task> taskList = ds.getLocalTasks();
		Iterator<Task> iter = taskList.iterator();
		
		while (iter.hasNext()){
			Task t = iter.next();
			if (t.getId().toString().equals(task.getId().toString())) {
				
				for (Request req : t) {
					//System.out.println(req.getDescription());
					assertEquals(req.getDescription(), r.getDescription());
				}
			}
		}
	}

}
