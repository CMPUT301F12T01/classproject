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

import java.io.Serializable;

import org.junit.Test;

import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.TextResponse;


/**
 * TextResponseTest -- Tests the TextResponse class to make sure all its methods work.
 * 
 * This class test to see if a TextResponse is created properly by first testing to see if
 * it exists. Then tests the getText method that gets the text of the TextResponse. After
 * it will test to see if setResponseData converts Serializable into String and then see if
 * it matches the pre-existing string. The last one tests to see if the getResponseData returns
 * the serializable and that if it is converted to a String it matches the pre-existing string.
 * 
 * @author padlesky
 *
 */
public class TextResponseTest {

	/**
	 * Creates a TextResponse and tests to see if it exists
	 */
	@Test
	public void addTextResponse () {
		TextResponse testTextResponse = new TextResponse("This is a text response.");
		assertNotNull(testTextResponse);
	}
	
	/**
	 * Tests to see if getText() returns the original string.
	 */
	@Test
	public void getTextResponse () {
		TextResponse testTextResponse = new TextResponse("This is a text response.");
		String getTestText = testTextResponse.getText();
		assertTrue(getTestText.equals("This is a text response."));
	}
	
	/**
	 * Tests to see if the setResponseData properly converts a 
	 * Serialized string to a string by checking to see if a 
	 * Serialized string matches the original string
	 * if it is converted back into a string.
	 */
	@Test
	public void setResponseData () {
		String randomString = "This is a text response.";
		TextResponse testTextResponse = new TextResponse(null);
		testTextResponse.setResponseData(randomString);
		assertTrue(testTextResponse.getText().equals(randomString));
	}

	/**
	 * Tests to see if the getResponseData properly gives a
	 * String into a Serialized string by checking to see if a
	 * Serialized string converted into a string matches the original
	 * String used.
	 */
	@Test
	public void getResponseData () {
		String randomString = "This is a text response.";
		TextResponse testTextResponse = new TextResponse(randomString);
		Serializable testGetResponseData = testTextResponse.getResponseData();
		assertTrue(((String) testGetResponseData).equals(randomString));
	}
	
	@Test
	public void getMediaType () {
		TextResponse testTextResponse = new TextResponse("This is a text response.");
		String media = testTextResponse.getMediaType().toString();
		assertTrue(testTextResponse.getMediaType().equals(MediaType.TEXT));
	}
	
}
