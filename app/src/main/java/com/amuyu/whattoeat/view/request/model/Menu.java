package com.amuyu.whattoeat.view.request.model;


import android.net.Uri;

public class Menu {

    Uri uri;
    String title;
    String situation;

    public Menu(Uri uri, String title, String situation) {
        this.uri = uri;
        this.title = title;
        this.situation = situation;
    }

    public Uri getUri() {
        return uri;
    }

    public String getTitle() {
        return title;
    }

    public String getSituation() {
        return situation;
    }
}
