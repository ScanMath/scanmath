package com.example.scanmath;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

/**
 * Created by user on 2/22/14.
 */
public class StartActivity extends Activity {
    private Context mContext;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        Button openCameraViewBttn = (Button) findViewById(R.id.camera_bttn);
        openCameraViewBttn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    photoFile = new File(mContext.getFilesDir(), "math_img.jpg");
                    try {
                        photoFile.createNewFile();
                        Log.e("file", "am creat" + photoFile.getPath());
                    }
                    catch (IOException ex) {
                        Log.e("file", "eroare la creere");
                    }

                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        setResult(RESULT_OK, takePictureIntent);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.e("result", "sunt in result");
            Intent dispImg = new Intent(mContext, DisplayPicture.class);
            startActivity(dispImg);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
