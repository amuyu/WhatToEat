package com.amuyu.whattoeat.view.foods;


import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.view.BasePresenter;
import com.amuyu.whattoeat.view.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface FoodsContract {
    interface View extends BaseView<Presenter> {
        void showFoods(List<Food> foods);
        void showRequest(ArrayList<Food> selectedFood);
        void showOverSelectedFood();
    }

    interface Presenter extends BasePresenter {
        void loadFoods();
        void askWhatToEat();

        void selectFood(Food food, boolean selected);
        void ask();
    }
}
