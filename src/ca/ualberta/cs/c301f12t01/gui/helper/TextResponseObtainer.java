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
import android.widget.TextView;
import ca.ualberta.cs.c301f12t01.common.Response;
import ca.ualberta.cs.c301f12t01.common.TextResponse;
import ca.ualberta.cs.c301f12t01.R;

/**
 * ObtainText -- Gets the text from a TextView and returns it as a Response
 * 
 * @author Bronte Lee <bronte@ualberta.ca>
 *
 */
public class TextResponseObtainer extends ResponseObtainer {

	private static int textViewResourceID = R.id.edit_text_fulfill;


	/**
	 * @param view
	 */
	public TextResponseObtainer(ViewGroup view) {
		super(view);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.gui.ObtainResponseInterface#getResponse()
	 */
	public Response getResponse() {
		// TODO Auto-generated method stub
		String textMedia = getText();

		return new TextResponse(textMedia);
	}


	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.gui.helper.ResponseObtainer#setupFulfillRequest()
	 */
	@Override
	public void setupFulfillRequest() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.gui.helper.ResponseObtainer#setupDisplayResponse(ca.ualberta.cs.c301f12t01.common.Response)
	 */
	@Override
	public void setupDisplayResponse(Response response) {
		// TODO Auto-generated method stub

		TextView textView = (TextView) getView().findViewById(R.id.text_response);

		/* Set these things up to make it look prettier! */
		textView.setText(response.getResponseData().toString());
		textView.setPadding(20, 10, 20, 10);
		textView.setTextSize(18);
		
	}
	
	private String getText() {
		TextView textView = (TextView) getView().findViewById(textViewResourceID);

		return textView.getText().toString();
		
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.c301f12t01.gui.helper.ResponseObtainer#hasBeenFulfilled()
	 */
	@Override
	public boolean hasBeenFulfilled() {
		// TODO Auto-generated method stub
		return !getText().equals("");
	}
}
