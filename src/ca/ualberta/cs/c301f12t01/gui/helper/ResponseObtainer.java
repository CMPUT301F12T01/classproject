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
package ca.ualberta.cs.c301f12t01.gui.helper;

import android.view.ViewGroup;
import ca.ualberta.cs.c301f12t01.common.Response;

/**
 * Abstract Class ObtainResponse for obtaining a response from a media type
 * 
 * @author Bronte Lee <bronte@ualberta.ca>
 *
 */
public abstract class ResponseObtainer {

	protected ViewGroup viewGroup;
	
	public ResponseObtainer(ViewGroup view){
		this.viewGroup = view; 
	}
	
	public abstract Response getResponse();
	
	/* Different set up methods for setting up the proper fields. */
	public abstract void setupFulfillRequest();
	
	public abstract void setupDisplayResponse(Response response);
	
	/** Returns whether the specified response has been fulfilled by the TaskFulfiller. */
	public abstract boolean hasBeenFulfilled();

	public ViewGroup getView() {
		return viewGroup;
	}

	public void setView(ViewGroup view) {
		this.viewGroup = view;
	}
	
	

}
