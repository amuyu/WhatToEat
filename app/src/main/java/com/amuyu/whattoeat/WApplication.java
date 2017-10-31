package com.amuyu.whattoeat;


import android.app.Application;

import com.amuyu.logger.DefaultLogPrinter;
import com.amuyu.logger.Logger;

public class WApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogPrinter(new DefaultLogPrinter(this));
    }
}
