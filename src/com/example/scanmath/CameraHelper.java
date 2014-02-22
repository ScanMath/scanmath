package com.example.scanmath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class CameraHelper extends SurfaceView implements Callback {

	private SurfaceHolder mHolder;
    private static Camera mCamera;
	private static Context mContext;
	private static File mPictureFile;
	private static final String TAG = "ERROR_LOG";
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;


	/** Check if this device has a camera */
	public static boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        return true;
	    } else {
	        return false;
	    }
	}
	
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
	    Camera camera = null;
	    try {
	        camera = Camera.open(); // attempt to get a Camera instance
            Camera.Parameters params= camera.getParameters();
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            params.setColorEffect(Camera.Parameters.EFFECT_MONO);
            params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            camera.setParameters(params);
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return camera; // returns null if camera is unavailable
	}
	
    public CameraHelper(Context context, Camera camera) {
        super(context);
        mContext = context;
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    	if (mCamera != null){
    		mCamera.release();
    		mCamera = null;
    	}
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) { }
    
    public static ShutterCallback mShutterCallback = new ShutterCallback() {
        public void onShutter() {
            AudioManager mgr = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            mgr.playSoundEffect(AudioManager.FLAG_PLAY_SOUND);
        }
    };
    
    public static PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

}
