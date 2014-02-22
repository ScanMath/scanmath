package com.example.scanmath;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
	
	protected static final String EXTRA_PICTURE_FILE = "picture_file";
	private Camera mCamera;
    private CameraHelper mPreview;
    private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mContext = this;
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);
		
		if(CameraHelper.checkCameraHardware(this)) {
			mCamera = CameraHelper.getCameraInstance();
			if(mCamera != null) {
				mPreview = new CameraHelper(this, mCamera);
				FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
				preview.addView(mPreview);
			}
		}
		else {
			// nothing to do because camera is not available :)
		}
	}
	
	public void displayPicture(View view) {
		// get an image from the camera
		mCamera.takePicture(CameraHelper.mShutterCallback, null, CameraHelper.mPicture);
		// Create the result Intent and include the picture
        Intent intent = new Intent(mContext, DisplayPicture.class);
        //intent.putExtra(EXTRA_PICTURE_FILE, CameraHelper.getmPictureFile());
        startActivity(intent);
        // Set result and finish this Activity
        //setResult(Activity.RESULT_OK, intent);
        //finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
    protected void onPause() {
        super.onPause();
        if(mCamera != null) {
        	mCamera.release();
        	mCamera = null;
        }
    }
	
}
