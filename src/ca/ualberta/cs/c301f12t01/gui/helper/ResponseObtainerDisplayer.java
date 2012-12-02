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

import java.lang.reflect.InvocationTargetException;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Response;

/**
 * Abstract ResponseDisplayer for displaying the responses, same as the
 * ResponseObtainer
 * 
 * @author Bronte Lee <bronte@ualberta.ca>
 * 
 */

/*
 * TODO: Generalize? The difference between ROO and ROD is the R.layout xml AND
 * ROD requires the response to be passed in to get the response information...
 */
public abstract class ResponseObtainerDisplayer {

	public static ResponseObtainer showResponseDisplayer(Response response,
			LayoutInflater inflater, ViewGroup root) {
		ResponseObtainer obtainer = null;
		int layout_id = -1;
		ViewGroup view = null;
		Class<? extends ResponseObtainer> obtainerClass = null;

		MediaType media = response.getMediaType();

		switch (media) {
		case TEXT:
			layout_id = R.layout.fragment_report_text;
			obtainerClass = TextResponseObtainer.class;
			break;
		case AUDIO:
			layout_id = R.layout.fragment_report_audio;
			obtainerClass = AudioResponseObtainer.class;
			break;
		case PHOTO:
			layout_id = R.layout.fragment_report_photo;
			obtainerClass = PhotoResponseObtainer.class;
			break;
		default:
			// err....
			// TODO: Give an error response obtainer?
		}

		view = (ViewGroup) inflater.inflate(layout_id, root);

		try {
			obtainer = obtainerClass.getConstructor(ViewGroup.class)
					.newInstance(view);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		/* Show response information */
		obtainer.setupDisplayResponse(response);

		return obtainer;
	}

}
