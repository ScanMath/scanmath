package com.example.smartmath;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
	
	private Camera mCamera;
    private CameraHelper mPreview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);
		if(CameraHelper.checkCameraHardware(this)) {
			mCamera = CameraHelper.getCameraInstance();
			mPreview = new CameraHelper(this, mCamera);
			FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
			preview.addView(mPreview);
			// Add a listener to the Capture button
			Button captureButton = (Button) findViewById(R.id.button_capture);
			captureButton.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// get an image from the camera
					mCamera.takePicture(CameraHelper.mShutterCallback, null, CameraHelper.mPicture);
				}
			}
					);
		}
		else {
			// nothing to do
		}
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
