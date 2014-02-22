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
    
    /** Create a file Uri for saving an image or video */
//    private static Uri getOutputMediaFileUri(int type){
//    	return Uri.fromFile(getOutputMediaFile(type));
//    }
    
    public static ShutterCallback mShutterCallback = new ShutterCallback() {
        public void onShutter() {
            AudioManager mgr = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            mgr.playSoundEffect(AudioManager.FLAG_PLAY_SOUND);
        }
    };
    
    public static PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

//            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
//            if (pictureFile == null){
//                Log.d(TAG, "Error creating media file, check storage permissions");
//                return;
//            }
//            try {
//                FileOutputStream fos = new FileOutputStream(pictureFile);
//                fos.write(data);
//                fos.close();
//                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
//                		Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
//            } catch (FileNotFoundException e) {
//                Log.d(TAG, "File not found: " + e.getMessage());
//            } catch (IOException e) {
//                Log.d(TAG, "Error accessing file: " + e.getMessage());
//            }
        }
    };
    
    /** Create a File for saving an image or video */
//    private static File getOutputMediaFile(int type){
//
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//                  Environment.DIRECTORY_PICTURES), "MyCameraApp");
//        if (! mediaStorageDir.exists()){
//            if (! mediaStorageDir.mkdirs()){
//                Log.d("MyCameraApp", "failed to create directory");
//                return null;
//            }
//        }
//        // Create a media file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile;
//        if (type == MEDIA_TYPE_IMAGE){
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//            "IMG_"+ timeStamp + ".jpg");
//        } else if(type == MEDIA_TYPE_VIDEO) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//            "VID_"+ timeStamp + ".mp4");
//        } else {
//            return null;
//        }
//        setmPictureFile(mediaFile);
//        return mediaFile;
//    }

//	public static File getmPictureFile() {
//		return mPictureFile;
//	}
//
//	public static void setmPictureFile(File mPictureFile) {
//		CameraHelper.mPictureFile = mPictureFile;
//	}

}
