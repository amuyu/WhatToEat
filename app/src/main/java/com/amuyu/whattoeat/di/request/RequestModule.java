package com.amuyu.whattoeat.di.request;


import com.amuyu.whattoeat.view.request.RequestContract;
import com.amuyu.whattoeat.view.request.model.RequestPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RequestModule {

    private final RequestContract.View view;

    public RequestModule(RequestContract.View view) {
        this.view = view;
    }

    @Provides
    RequestContract.View provideView() {
        return view;
    }

    @Provides
    RequestContract.Presenter providePresenter(RequestPresenter presenter) {
        return presenter;
    }

}
