package ca.ualberta.cs.c301f12t01.gui.helper;

import ca.ualberta.cs.c301f12t01.R;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class RecordAudio extends Activity {

    private static final int RQS_RECORDING = 1;
    Uri savedUri;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_audio);
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, RQS_RECORDING);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	 // TODO Auto-generated method stub
		if(requestCode == RQS_RECORDING){
			savedUri = data.getData();
			
	 	}
		finish();
	}

	 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_record_audio, menu);
        return true;
    }
}
