package com.amuyu.whattoeat.di.foods;

import com.amuyu.whattoeat.view.foods.FoodsContract;
import com.amuyu.whattoeat.view.foods.FoodsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class FoodsModule {

    private final FoodsContract.View view;
    private final String situationId;

    public FoodsModule(FoodsContract.View view, String situationId) {
        this.view = view;
        this.situationId = situationId;
    }

    @Provides
    FoodsContract.View provideView() {
        return view;
    }

    @Provides
    @FoodScope
    String provideSituationId() {
        return situationId;
    }

    @Provides
    FoodsContract.Presenter providePresenter(FoodsPresenter presenter) {
        return presenter;
    }
}
