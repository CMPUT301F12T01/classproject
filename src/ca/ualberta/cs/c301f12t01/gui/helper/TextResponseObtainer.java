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
		int textID = R.id.edit_text_fulfill;
		
		TextView textView = (TextView) getView().findViewById(textID);
		
		String textMedia = textView.getText().toString();
		
		return new TextResponse(textMedia);

	}


}