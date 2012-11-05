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
 * 
 * @author padlesky
 *
 */
public class ReportTest {

	@Test
	public void creatingReport() {
		Task task = TestUtils.makeSimpleTask();
		Report testReport = new Report(task);
		assertNotNull(testReport);
	}
	
	@Test
	public void getTimestamp() {
		Task task = TestUtils.makeSimpleTask();
		Timestamp timestamp = new Timestamp(new Date().getTime());
		Report testReport = new Report(task, timestamp);
		assertTrue(testReport.getTimestamp().equals(timestamp));
	}
	
	@Test
	public void createResponse() {
		Response textResponse = new TextResponse("This is a text response.");
		assertNotNull(textResponse);
	}
	/* 
	 * Not sure how to add a response to test whether the the addResponse
	 * works or not. This also affects the other 3 tests.
	 */
	@Test
	public void addResponse() {
		Response textResponse = new TextResponse("This is a text response.");
		Task task = TestUtils.makeSimpleTask();
		Report testReport = new Report(task);
		boolean test = testReport.addResponse(textResponse);
		assertTrue(test);
	}
	
	@Test
	public void removeTextResponse() {
		
	}
	
	@Test
	public void responseCount() {
		
	}
	
	@Test
	public void responseTypes() {
		
	}
}
