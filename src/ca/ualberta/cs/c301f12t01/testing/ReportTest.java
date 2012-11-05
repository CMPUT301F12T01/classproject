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

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Response;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.common.TextResponse;


/**
 * ReportTest -- A JUnit test to verify the methods for Report work.
 * 
 * 
 * @author padlesky
 */
public class ReportTest {

	/**
	 * Test to see if creating a Report exists.
	 */
	@Test
	public void creatingReport() {
		Task task = TestUtils.makeSimpleTask();
		Report testReport = new Report(task);
		assertNotNull(testReport);
	}
	
	/**
	 * Test to see if timestamps are set properly. Also
	 * tests the getTimestamp function at the same time.
	 */
	@Test
	public void getTimestamp() {
		Task task = TestUtils.makeSimpleTask();
		Timestamp timestamp = new Timestamp(new Date().getTime());
		Report testReport = new Report(task, timestamp);
		assertTrue(testReport.getTimestamp().equals(timestamp));
	}
	
	/**
	 * Tests to see if you can make a TextResponse a Response.
	 */
	@Test
	public void createResponse() {
		Response textResponse = new TextResponse("This is a text response.");
		assertNotNull(textResponse);
	}
	
	/**
	 * Tests to see if you can add a Response to a Report.
	 */
	@Test
	public void addResponse() {
		Response textResponse = new TextResponse("This is a text response.");
		Task task = TestUtils.makeSimpleTask();
		Report testReport = new Report(task);
		boolean test = testReport.addResponse(textResponse);
		assertTrue(test);
	}
	
	/**
	 * Tests to see if you can remove a Response from a Report.
	 */
	@Test
	public void removeTextResponse() {
		Response textResponse = new TextResponse("This is a text response.");
		Task task = TestUtils.makeSimpleTask();
		Report testReport = new Report(task);
		testReport.addResponse(textResponse);
		boolean test = testReport.removeResponse(textResponse);
		assertTrue(test);
	}
	
	/**
	 * Gets the number of responses that were given.
	 */
	@Test
	public void responseCount() {
		Response textResponse = new TextResponse("This is a text response.");
		Task task = TestUtils.makeSimpleTask();
		Report testReport = new Report(task);
		testReport.addResponse(textResponse);
		assertTrue(testReport.responseCount() == 1);
	}
	
	/**
	 * Checks to see if the responseTypes returns the Types in a proper string format.
	 */
	@Test
	public void responseTypes() {
		Response textResponse = new TextResponse("This is a text response.");
		Task task = TestUtils.makeSimpleTask();
		Report testReport = new Report(task);
		testReport.addResponse(textResponse);
		assertTrue(testReport.responseTypes().matches("TEXT "));
	}
}
