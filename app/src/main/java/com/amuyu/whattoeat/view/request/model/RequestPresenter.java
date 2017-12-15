package com.amuyu.whattoeat.view.request.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;

import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.view.request.RequestContract;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.network.storage.ImageUploadResponse;
import com.kakao.util.helper.MediaUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RequestPresenter implements RequestContract.Presenter {

    private final int TYPE_KAKAO = 0;
    private final int TYPE_INSTA = 1;

    private final String FILE_NAME = "foods.jpg";
    private final String FILE_PATH;
    private final RequestContract.View view;

    private List<Food> mSelectedFoods = new ArrayList<>();
    private String mSituationName;


    @Inject
    public RequestPresenter(Context context, RequestContract.View view) {
        FILE_PATH = context.getExternalCacheDir().getPath() + "/" + FILE_NAME;
        this.view = view;
    }

    @Override
    public void start(List<Food> foods, String name) {
        mSelectedFoods = foods;
        mSituationName = name;
        view.showFoodImage(foods);
    }

    @Override
    public void shareInsta() {
        Menu menu = makeMenu(TYPE_INSTA);
        if (menu != null) {
            view.showShareInsta(menu);
        }
    }

    @Override
    public void shareKakao(Context context) {
        final Menu menu = makeMenu(TYPE_KAKAO);
        if (menu != null) {
            try {
                File imageFile = new File(MediaUtils.getImageFilePathFromUri(menu.getUri(), context));

                KakaoLinkService.getInstance().uploadImage(context, false, imageFile, new ResponseCallback<ImageUploadResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        com.kakao.util.helper.log.Logger.e(errorResult.toString());
                        view.showKakaoErrorMessage(errorResult.toString());
                    }

                    @Override
                    public void onSuccess(ImageUploadResponse result) {
                        String imageUrl = result.getOriginal().getUrl();
                        view.showShareKakao(menu, imageUrl);
                    }
                });
            } catch (FileNotFoundException e) {
                com.kakao.util.helper.log.Logger.e(e.toString());
                view.showKakaoErrorMessage(e.toString());
            }
        }
    }

    @Override
    public void randomSelection() {

    }

    private Menu makeMenu(int type) {
        Bitmap bitmap = view.makeBitmap();
        if (bitmap == null) throw new NullPointerException("Can't make Bitmap!!!");

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(FILE_PATH);


            float ratio = (float)bitmap.getHeight() / (float) bitmap.getWidth();
            Logger.d("makeImage@@@@!! ratio:"+ratio);

            int resizeHeight = (int) (ratio * 960);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 960, resizeHeight, true);

            if (type == TYPE_INSTA) {
                Bitmap instaBitmap = Bitmap.createBitmap(960, 960, Bitmap.Config.ARGB_8888);
                Canvas instaCanvas = new Canvas(instaBitmap);
                instaCanvas.drawColor(Color.rgb(227,231,237));
                instaCanvas.drawBitmap(resized, 0, 480 - resizeHeight/2, null);
                instaBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } else {
                resized.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }

            File f = new File(FILE_PATH);
            Uri uri = Uri.fromFile(f);
            return new Menu(uri, getFoodsName(), mSituationName);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private String getFoodsName() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Food food : mSelectedFoods) {
            stringBuilder.append(food.getName());
            stringBuilder.append(",");
        }

        String result = stringBuilder.toString();
        return result.substring(0, result.length() - 1);
    }

}
