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

import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Response;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

/**
 * 
 * @author Bronte Lee
 *
 */
public class PhotoResponseObtainer extends ResponseObtainer {

	/**
	 * @param view
	 */
	public PhotoResponseObtainer(ViewGroup view) {
		super(view);
		// TODO Auto-generated constructor stub
	}

	// TODO implement get photo response
	public Response getResponse() {
		return null;

	}

	/* We need to be able to click on a button to take a photo */
	public void setButton() {

		TextView button = (TextView) getView().findViewById(R.id.button_take_photo);		

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				Log.d("Act-lifecycle", "Clicked Photo Button");
			}
		});  

	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.gui.helper.ResponseObtainer#setupFulfillRequest()
	 */
	@Override
	public void setupFulfillRequest() {
		// TODO Auto-generated method stub
		setButton();
		
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.gui.helper.ResponseObtainer#setupDisplayResponse(ca.ualberta.cs.c301f12t01.common.Response)
	 */
	@Override
	public void setupDisplayResponse(Response response) {
		// TODO Auto-generated method stub
		
	}

}
