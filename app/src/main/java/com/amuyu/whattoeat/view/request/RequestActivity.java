package com.amuyu.whattoeat.view.request;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.R;
import com.amuyu.whattoeat.data.repo.remote.IFirebaseRepo;
import com.amuyu.whattoeat.di.request.RequestModule;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.infra.GlideApp;
import com.amuyu.whattoeat.view.WApplication;
import com.amuyu.whattoeat.view.random.RandomActivity;
import com.amuyu.whattoeat.view.request.model.Menu;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RequestActivity extends AppCompatActivity implements RequestContract.View {

    public static final String FOOD_LIST = "food_list";
    public static final String SITUATION_NAME = "situation_name";

    @Inject
    public IFirebaseRepo repo;

    @Inject
    public RequestContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample);
        initializeDagger();
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.btnRandom).setClickable(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start((ArrayList<Food>)getIntent().getSerializableExtra(FOOD_LIST),
                getIntent().getStringExtra(SITUATION_NAME));
    }

    private void initializeDagger() {
        WApplication app = (WApplication)getApplication();
        app.getApplicationComponent()
                .requestComponent(new RequestModule(this))
                .inject(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInsta:
                presenter.shareInsta();
                break;
            case R.id.btnKakao:
                presenter.shareKakao(this);
                break;
            case R.id.btnRandom:
                view.setClickable(false);
                Intent intent = new Intent(RequestActivity.this, RandomActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void showFoodImage(List<Food> foods) {
        ArrayList<ImageView> views = new ArrayList<>();
        views.add((ImageView)findViewById(R.id.imageView1));
        views.add((ImageView)findViewById(R.id.imageView2));
        views.add((ImageView)findViewById(R.id.imageView3));
        views.add((ImageView)findViewById(R.id.imageView4));


        if (foods.size() > 0) {
            for (int i=0; i<foods.size();i++) {
                repo.loadImage(GlideApp.with(this), foods.get(i).getId())
                        .centerCrop()
                        .into(views.get(i));
            }
        }
    }

    @Override
    public void showShareInsta(Menu menu) {

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        String type = "image/*";
        // Set the MIME type
        share.setType(type);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, menu.getUri());
        share.putExtra(Intent.EXTRA_SUBJECT, "hello");
        share.putExtra(Intent.EXTRA_TEXT, "#뭐먹지");
        share.setPackage("com.instagram.android");

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }

    @Override
    public void showShareKakao(Menu menu, String url) {
        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder(getString(R.string.request_title, menu.getTitle()),
                        url, LinkObject.newBuilder().build())
                        .setDescrption(menu.getSituation())
                        .build())
                .addButton(new ButtonObject("이 앱이 궁금해?",
                        LinkObject.newBuilder().build()))
                .build();


        KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                com.kakao.util.helper.log.Logger.e(errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                Logger.d("");
            }
        });
    }

    @Override
    public void showRoulette(List<Food> selectedFood) {
        // TODO
    }

    @Override
    public void showKakaoErrorMessage(String message) {
        toastMessage(message);
    }

    @Override
    public Bitmap makeBitmap() {
        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.layout);
        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(layout.getMeasuredWidth(), layout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layout.draw(canvas);
        return bitmap;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
