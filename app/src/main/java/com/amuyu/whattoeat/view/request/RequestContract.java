package com.amuyu.whattoeat.view.request;


import android.content.Context;
import android.graphics.Bitmap;

import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.view.request.model.Menu;

import java.util.List;

public interface RequestContract {

    interface View {
        void showFoodImage(List<Food> foods);

        void showShareInsta(Menu menu);
        void showShareKakao(Menu menu, String url);
        void showRoulette(List<Food> selectedFood);

        void showKakaoErrorMessage(String message);

        Bitmap makeBitmap();
    }

    interface Presenter {
        void start(List<Food> foods, String name);
        void shareInsta();
        void shareKakao(Context context);
        void randomSelection();
    }
}
