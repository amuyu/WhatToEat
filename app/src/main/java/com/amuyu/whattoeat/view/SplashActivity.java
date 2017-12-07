package com.amuyu.whattoeat.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.amuyu.whattoeat.MainActivity;
import com.amuyu.whattoeat.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.concurrent.TimeUnit;

import app.dinus.com.loadingdrawable.LoadingView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        LoadingView loadingView = (LoadingView) findViewById(R.id.swap_view);
        loadingView.stopAnimation();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
