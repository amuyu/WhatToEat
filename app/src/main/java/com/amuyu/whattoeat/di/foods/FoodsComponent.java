package com.amuyu.whattoeat.di.foods;

import com.amuyu.whattoeat.view.foods.FoodsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = FoodsModule.class)
public interface FoodsComponent {
    void inject(FoodsFragment fragment);
}
