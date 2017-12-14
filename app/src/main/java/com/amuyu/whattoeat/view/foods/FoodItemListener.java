package com.amuyu.whattoeat.view.foods;


import android.widget.ImageView;

import com.amuyu.whattoeat.domain.model.Food;

public interface FoodItemListener {
    void onItemClick(Food food, boolean selected);
    void onDrawImage(final ImageView imageView, final String name);
}
