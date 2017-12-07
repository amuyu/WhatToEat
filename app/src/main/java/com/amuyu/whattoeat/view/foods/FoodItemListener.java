package com.amuyu.whattoeat.view.foods;


import android.widget.ImageView;

import com.amuyu.whattoeat.domain.model.Food;

public interface FoodItemListener {
    void onItemClick(Food food);
    void onDrawImage(final ImageView imageView);
}
