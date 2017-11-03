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
import android.widget.Toast;

import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.R;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.network.storage.ImageUploadResponse;
import com.kakao.util.helper.MediaUtils;

import java.io.File;
import java.io.FileNotFoundException;
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
        ((Button)findViewById(R.id.btnInsta)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareInsta();
            }
        });

        ((Button)findViewById(R.id.btnKakao)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                shareKakao();
//                sendDefaultFeedTemplate();
                uploadImage();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void shareKakao() {
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
        share.setPackage("com.kakao.talk");

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }



    void shareInsta() {
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

    private void uploadImage() {


        String mediaPath = Environment.getExternalStorageDirectory() + "/profile.jpg";

        // Create the URI from the media
        Logger.d("path!!:"+mediaPath);
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);

        try {
            File imageFile = new File(MediaUtils.getImageFilePathFromUri(uri, this));

            KakaoLinkService.getInstance().uploadImage(this, false, imageFile, new ResponseCallback<ImageUploadResponse>() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    com.kakao.util.helper.log.Logger.e(errorResult.toString());
                    Toast.makeText(getApplicationContext(), errorResult.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(ImageUploadResponse result) {
                    String imageUrl = result.getOriginal().getUrl();
                    Logger.d("imageUrl:"+ imageUrl);
                    sendDefaultFeedTemplate(imageUrl);
                    Toast.makeText(SampleActivity.this, "Successfully uploaded image at " + imageUrl, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (FileNotFoundException e) {
            com.kakao.util.helper.log.Logger.e(e.toString());
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void sendDefaultFeedTemplate(String url) {
        Logger.d("");
        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("딸기 치즈 케익",
                        url,
                        LinkObject.newBuilder().setWebUrl("레스토랑에서 뭐 먹을지 알려줘")
                                .setMobileWebUrl("https://developers.kakao.com").build())
                        .setDescrption("#케익 #딸기 #삼평동 #카페 #분위기 #소개팅")
                        .build())
                .addButton(new ButtonObject("이 앱이 궁금해?", LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()))
                .build();


        KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.d("");
                com.kakao.util.helper.log.Logger.e(errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                Logger.d("");
            }
        });
    }
}
