package ca.ualberta.cs.c301f12t01.gui.helper;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

public class TakePhotoActivity extends Activity {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	//private static final String ENCODED_IMAGE = "encoded image";
	public static String encodedImage;
	public static String photoType;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_take_photo);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/mp/")));
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		//finish();
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.d("Act-lifecycle", "We made it here!");
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == Activity.RESULT_OK) {
	            Bitmap bmp = (Bitmap) data.getExtras().get("data");
	            ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
	            photoType = CompressFormat.PNG.toString().toLowerCase();
	            byte[] byteArray = stream.toByteArray();
	            encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
	    		Log.d("Act-lifecycle", encodedImage);
	    		finish();
	        } else if (resultCode == Activity.RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
