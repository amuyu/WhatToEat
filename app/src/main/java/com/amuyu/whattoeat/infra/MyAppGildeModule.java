package com.amuyu.whattoeat.infra;


import android.content.Context;

import com.amuyu.logger.Logger;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

@GlideModule
public class MyAppGildeModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        Logger.d("");
        registry.append(StorageReference.class, InputStream.class, new FirebaseImageLoader.Factory());
    }
}
