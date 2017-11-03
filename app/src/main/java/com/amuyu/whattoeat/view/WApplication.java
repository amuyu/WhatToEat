package com.amuyu.whattoeat.view;


import android.app.Application;

import com.amuyu.logger.DefaultLogPrinter;
import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.di.repo.ApplicationComponent;
import com.amuyu.whattoeat.di.repo.ApplicationModule;
import com.amuyu.whattoeat.di.repo.DaggerApplicationComponent;
import com.tsengvn.typekit.Typekit;

public class WApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogPrinter(new DefaultLogPrinter(this));

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "ya_bold.ttf"))
                .addBold(Typekit.createFromAsset(this, "jua.ttf"));
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }
}
