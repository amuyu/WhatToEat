package com.amuyu.whattoeat.view.foods;


import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.data.repo.Repository;
import com.amuyu.whattoeat.di.foods.FoodScope;
import com.amuyu.whattoeat.domain.model.Food;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class FoodsPresenter implements FoodsContract.Presenter {

    private final FoodsContract.View view;
    private final Repository repository;
    private final String situationId;

    @Inject
    public FoodsPresenter(FoodsContract.View view, Repository repository, @FoodScope String situationId) {
        this.view = view;
        this.repository = repository;
        this.situationId = situationId;
    }

    @Override
    public void initialize() {
        loadFoods();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void loadFoods() {
        repository.getFoods(situationId)
                .subscribe(new Consumer<List<Food>>() {
                    @Override
                    public void accept(List<Food> foods) throws Exception {
                        view.showFoods(foods);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(""+throwable.getMessage());
                    }
                });
    }

    @Override
    public void askWhatToEat() {
        Logger.d("");
    }
}
