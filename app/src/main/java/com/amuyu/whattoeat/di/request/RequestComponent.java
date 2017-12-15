package com.amuyu.whattoeat.di.request;

import com.amuyu.whattoeat.di.repo.FirebaseModule;
import com.amuyu.whattoeat.view.request.RequestActivity;

import dagger.Subcomponent;

@Subcomponent(modules = { RequestModule.class, FirebaseModule.class })
public interface RequestComponent {
    void inject(RequestActivity activity);
}
