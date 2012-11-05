package ca.ualberta.cs.c301f12t01.testing;

import java.util.Collection;
import java.util.Iterator;

import android.test.AndroidTestCase;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Response;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.common.TextResponse;
import ca.ualberta.cs.c301f12t01.localStorage.DeviceStorage;
import ca.ualberta.cs.c301f12t01.testing.TestUtils;

/**
 * Test class from the DatabaseTesting android project
 * 
 * This class attempts to test all of the functionality
 * of storing and retrieving Reports and Requests from 
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

public class ReportLocalStorageTest extends AndroidTestCase
{
	public void test_store_localreport() 
	{
		Task task = TestUtils.makeSimpleTask();
		Report report = TestUtils.makeSimpleReport(task);
		DeviceStorage ds = new DeviceStorage(getContext());
		
		ds.storeReport(report);
	}
	
	public void test_retrieve_localreport() 
	{
		Task task = TestUtils.makeSimpleTask();
		Report report = TestUtils.makeSimpleReport(task);
		DeviceStorage ds = new DeviceStorage(getContext());
		
		ds.storeReport(report);
		Collection<Report> reportList = ds.getLocalReports(task.getId());
		assertNotNull(reportList);
		
	}
	
	public void test_retrieve_globalreport() 
	{
		// Put in 1 global report
		Task task = TestUtils.makeSimpleTask();
		Report report = TestUtils.makeSimpleReport(task);
		DeviceStorage ds = new DeviceStorage(getContext());
		
		ds.storeReport(report);
		Collection<Report> reportList = ds.getGlobalReports(task.getId());
		assertEquals(1, reportList.size());
		
	}
	
	public void test_retrieve_samelocalreport() 
	{
		Task task = TestUtils.makeSimpleTask();
		Report report = TestUtils.makeSimpleReport(task);
		DeviceStorage ds = new DeviceStorage(getContext());
		
		ds.storeReport(report);
		Collection<Report> reportList = ds.getLocalReports(task.getId());
		Iterator<Report> iter = reportList.iterator();
		
		while (iter.hasNext()){
			Report r = iter.next();
			if (r.getId().toString().equals(report.getId().toString())) {
				assertEquals(report.getId().toString(), r.getId().toString());
			}
		}
	}
	
	public void test_retrieve_responses() 
	{
		Task task = TestUtils.makeSimpleTask();
		Report report = TestUtils.makeSimpleReport(task);
		Response res = new TextResponse("RESPONSE YAY");
		report.addResponse(res);
		DeviceStorage ds = new DeviceStorage(getContext());
		
		ds.storeReport(report);
		Collection<Report> reportList = ds.getLocalReports(task.getId());
		Iterator<Report> iter = reportList.iterator();
		
		while (iter.hasNext()){
			Report r = iter.next();
			if (r.getId().toString().equals(report.getId().toString())) {
				
				for (Response response : r) {
					//System.out.println(response.getResponseData());
					assertEquals(response.getResponseData(), "RESPONSE YAY");
					
				}
			}
		}
		
	}

}
