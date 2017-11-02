package com.amuyu.whattoeat.view;


public interface BaseView<T> {
    void initializeDagger();
    void setPresenter(T presenter);
    void showLoading();
    void hideLoading();
}
