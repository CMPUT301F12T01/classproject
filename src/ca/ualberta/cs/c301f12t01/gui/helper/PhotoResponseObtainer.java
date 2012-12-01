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

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private String encodedImage;
	private Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private byte[] byteArray;
	/**
	 * @param view
	 */
	public PhotoResponseObtainer(ViewGroup view) {
		super(view);
		// TODO Auto-generated constructor stub
	}

	// TODO implement get photo response
	public Response getResponse() {
		return new PhotoResponse(encodedImage, CompressFormat.PNG);
	}

	/* We need to be able to click on a button to take a photo */
	public void setButton() {

		TextView button = (TextView) getView().findViewById(R.id.button_take_photo);		

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				//Hope hope = new Hope();
				startPhoto();
				encodedImage = getPhoto();

				Log.d("Act-lifecycle", "Clicked Photo Button");
			}
		});  

	}

	//private class Hope extends Activity {
		
		//create new Intent
		//Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
//		@Override
//		public void onCreate(Bundle savedInstanceState) {
//			super.onCreate(savedInstanceState);
//			startPhoto(getApplicationContext());
//		}
//		//Hope's String
		//byte[] byteArray;
		public void startPhoto() {
			// start the video Capture Intent
			//intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/mp/")));
			//((Activity) this.getApplicationContext()).startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			//((Activity) this.getApplicationContext()).startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		}

		public void onActivityResult(int requestCode, int resultCode, Intent data) {        
		    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
		        if (resultCode == Activity.RESULT_OK) {
		            Bitmap bmp = (Bitmap) data.getExtras().get("data");
		            ByteArrayOutputStream stream = new ByteArrayOutputStream();
		            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		            byteArray = stream.toByteArray();

		        } else if (resultCode == Activity.RESULT_CANCELED) {
		            // User cancelled the image capture
		        } else {
		            // Image capture failed, advise user
		        }
		    }               
		}
		
		public String getPhoto() {
			String encodeImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
			return encodeImage;
		}
	//}
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
		ImageView imageView = (ImageView) getView().findViewById(R.id.image_response);
		byte[] decoded = Base64.decode(encodedImage, Base64.DEFAULT);  
		Bitmap bitMap = BitmapFactory.decodeByteArray(decoded , 0, decoded.length);
		imageView.setImageBitmap(bitMap);
	}

}
