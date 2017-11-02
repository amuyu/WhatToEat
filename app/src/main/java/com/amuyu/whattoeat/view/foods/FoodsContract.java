package com.amuyu.whattoeat.view.foods;


import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.view.BasePresenter;
import com.amuyu.whattoeat.view.BaseView;

import java.util.List;

public interface FoodsContract {
    interface View extends BaseView<Presenter> {
        void showFoods(List<Food> foods);
    }

    interface Presenter extends BasePresenter {
        void loadFoods();
        void askWhatToEat();
    }
}
