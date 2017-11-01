package com.amuyu.whattoeat.di.situations;

import com.amuyu.whattoeat.view.situations.SituationsContract;
import com.amuyu.whattoeat.view.situations.SituationsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SituationModule {

    private final SituationsContract.View view;

    public SituationModule(SituationsContract.View view) {
        this.view = view;
    }

    @Provides
    SituationsContract.View provideView() {
        return view;
    }

    @Provides
    SituationsContract.Presenter providePresenter(SituationsPresenter presenter) {
        return presenter;
    }
}
