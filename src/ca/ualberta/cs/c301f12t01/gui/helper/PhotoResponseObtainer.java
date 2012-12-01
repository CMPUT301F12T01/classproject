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

import java.io.ByteArrayOutputStream;
import java.io.File;

import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Response;
import ca.ualberta.cs.c301f12t01.common.PhotoResponse;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Bronte Lee
 * @author padlesky
 *
 */
public class PhotoResponseObtainer extends ResponseObtainer {

	private static final String ENCODED_IMAGE = "encoded image";
	private String encodedImage;
	private String photoType;
	
	/**
	 * @param view
	 */
	public PhotoResponseObtainer(ViewGroup view) {
		super(view);
		// TODO Auto-generated constructor stub
	}

	// TODO implement get photo response
	public Response getResponse() {
		encodedImage = TakePhotoActivity.encodedImage;
		photoType = TakePhotoActivity.photoType;
		//Log.d("Act-lifecycle", encodedImage);
		return new PhotoResponse(encodedImage, photoType);
	}

	/* We need to be able to click on a button to take a photo */
	public void setButton() {

		final TextView button = (TextView) getView().findViewById(R.id.button_take_photo);		

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				Intent intent = new Intent (button.getContext(), TakePhotoActivity.class);
				((Activity) button.getContext()).startActivityForResult(intent, 1);
			}
		});  

//		@Override
//		public void onActivityResult(int requestCode, int resultCode, Intent data) {
//			super.onActivityResult(requestCode, resultCode, data);
//			switch(requestCode) {
//				case (1) : {
//					if (resultCode == Activity.RESULT_OK) {
//						encodedImage = data.getStringExtra(ENCODED_IMAGE);
//					}
//					break;
//				}
//			}
//		}
		
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
		Log.d("Act-lifecycle", "did i make it here?");
		PhotoResponse photoResponse = (PhotoResponse) response;
		String encodeImage = photoResponse.getPhoto();
		//Log.d("Act-lifecycle", encodeImage);
		ImageView imageView = (ImageView) getView().findViewById(R.id.image_response);
		byte[] decoded = Base64.decode(encodeImage, Base64.DEFAULT);  
		Bitmap bitMap = BitmapFactory.decodeByteArray(decoded , 0, decoded.length);
		imageView.setImageBitmap(bitMap);
	}

}
