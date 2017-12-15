package com.amuyu.whattoeat.view.foods;


import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.data.repo.Repository;
import com.amuyu.whattoeat.di.foods.FoodScope;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Situation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class FoodsPresenter implements FoodsContract.Presenter {

    private final FoodsContract.View view;
    private final Repository repository;
    private final String situationId;
    private HashMap<String, Food> mSeletedFoods;

    @Inject
    public FoodsPresenter(FoodsContract.View view, Repository repository, @FoodScope String situationId) {
        this.view = view;
        this.repository = repository;
        this.situationId = situationId;
        mSeletedFoods = new LinkedHashMap<>();
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
    public void selectFood(Food food, boolean selected) {
        if (mSeletedFoods.size() > 4) {
            view.showOverSelectedFood();
            return ;
        }

        if (selected)
            mSeletedFoods.put(food.getId(), food);
        else
            mSeletedFoods.remove(food.getId());
    }

    @Override
    public void ask() {
        Logger.d("sId:"+situationId);
        Situation situation = repository.getSituation(situationId);
        view.showRequest(new ArrayList<Food>(mSeletedFoods.values()), situation.getName());
    }
}
