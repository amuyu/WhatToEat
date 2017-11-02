package com.amuyu.whattoeat.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class SampleActivity extends AppCompatActivity {

    private static final int FROG_ID = 212121;
    Context context;
    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        context = this;
        ((Button)findViewById(R.id.btnUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test2();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    void test2() {
        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        String type = "image/*";
        // Set the MIME type
        share.setType(type);

        try {
            makeImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mediaPath = Environment.getExternalStorageDirectory() + "/profile.jpg";

        // Create the URI from the media
        Logger.d("path!!:"+mediaPath);
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_SUBJECT, "hello");
        share.putExtra(Intent.EXTRA_TEXT, "#뭐먹지");
        share.setPackage("com.instagram.android");

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }

    void makeImage() throws IOException {
        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.layout);
        Logger.d("w:"+layout.getWidth());
        Logger.d("h:"+layout.getMeasuredHeight());
        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();


        Bitmap bitmap = Bitmap.createBitmap(layout.getMeasuredWidth(), layout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layout.draw(canvas);

        if (bitmap != null) {

            String mediaPath = Environment.getExternalStorageDirectory() + "/profile.jpg";
            FileOutputStream out = new FileOutputStream(mediaPath);

            float ratio = (float)bitmap.getHeight() / (float) bitmap.getWidth();
            Logger.d("makeImage@@@@!! ratio:"+ratio);

            int resizeHeight = (int) (ratio * 960);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 960, resizeHeight, true);

            Bitmap instaBitmap = Bitmap.createBitmap(960, 960, Bitmap.Config.ARGB_8888);
            Canvas instaCanvas = new Canvas(instaBitmap);
            instaCanvas.drawColor(Color.rgb(227,231,237));
            instaCanvas.drawBitmap(resized, 0, 480 - resizeHeight/2, null);
            instaBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            String mediaPath2 = Environment.getExternalStorageDirectory() + "/temp.jpg";
            FileOutputStream out2 = new FileOutputStream(mediaPath2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out2);

            out.close();
        }
    }
}
