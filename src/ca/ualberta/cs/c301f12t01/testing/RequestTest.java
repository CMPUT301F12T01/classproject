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

import org.junit.Test;

import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Request;


/**
 * RequestTest -- Test to see if the Request can be made and that its methods work.
 * 
 * @author padlesky
 */
public class RequestTest {

	/**
	 * Test to see if Audio Requests can be created.
	 */
	@Test
	public void creatingAudioRequest() {
		Request request = new Request(MediaType.AUDIO);
		assertNotNull(request);
	}
	
	/**
	 * Test to see if Photo Requests can be created.
	 */
	@Test
	public void creatingPhotoRequest() {
		Request request = new Request(MediaType.PHOTO);
		assertNotNull(request);
	}
	
	/**
	 * Test to see if Text Requests can be created.
	 */
	@Test
	public void creatingTextRequest() {
		Request request = new Request(MediaType.TEXT);
		assertNotNull(request);
	}

	/**
	 * Test to see if the method get type returns the MediaType properly.
	 */
	@Test
	public void getType() {
		Request request = new Request(MediaType.TEXT);
		assertTrue(request.getType().equals(MediaType.TEXT));
	}
	
	/**
	 * Test to see if you can set a description to a request and also if 
	 * you can get the description of a request.
	 */
	@Test
	public void setGetDescription() {
		Request request = new Request(MediaType.TEXT);
		request.setDescription("This now has power!");
		assertTrue(request.getDescription().matches("This now has power!"));
	}
	
	/**
	 * Test to see if you can get an a quantity to a request and also if 
	 * you can get the quantity of a request.
	 */
	@Test
	public void setGetQuantity() {
		Request request = new Request(MediaType.TEXT);
		request.setQuantity(1000);
		assertTrue(request.getQuantity() == 1000);
	}
}
