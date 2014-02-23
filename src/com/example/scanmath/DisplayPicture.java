package com.example.scanmath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DisplayPicture extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_picture);
		// Show the Up button in the action bar.
		setupActionBar();
        ImageView imgSlot = (ImageView) findViewById(R.id.imageView);
        File img_file = new File(this.getFilesDir().getPath() + "/math_img.jpg");
        FileInputStream fin = null;
        byte[] byte_img = new byte[(int) img_file.length()];
        try {
//            fin = openFileInput("math_img.jpg");
            fin = new FileInputStream(img_file);
        }
        catch (IOException ex) {
            Log.e("read", "Can not read img file");
        }
        if (fin != null) {
            try {
                fin.read(byte_img);
            }
            catch (IOException ex) {
                Log.e("read", "can not read img file");
            }
        }
        try {
            fin.close();
        }
        catch (IOException ex) {
            Log.e("file", "can not close file");
        }
        if (byte_img.length < 100) {
            Log.e("file", "bad img file");
        }
        Bitmap bm = BitmapFactory.decodeByteArray(byte_img, 0, byte_img.length);
//        imgSlot.setImageBitmap(bm);
        Log.e("file", "file size: " + img_file.length());
        imgSlot.setImageURI(Uri.fromFile(img_file));
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_picture, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
