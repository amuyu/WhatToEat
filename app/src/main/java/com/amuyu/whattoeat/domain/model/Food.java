package com.amuyu.whattoeat.domain.model;


import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.UUID;

public class Food implements Serializable {
    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("sid")
    private String sid;

    @SerializedName("hasImage")
    private boolean hasImage;

    public Food() {
        // Firebase Database
    }

    public Food(String sid, String name) {
        this(UUID.randomUUID().toString(), name, sid);
    }

    public Food(String id, String name, String sid) {
        this(id,name,sid,false);
    }

    public Food(String id, String name, String sid, boolean hasImage) {
        this.id = id;
        this.name = name;
        this.sid = sid;
        this.hasImage = hasImage;
    }


    public String getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equal(id, food.id) &&
                Objects.equal(name, food.name) &&
                Objects.equal(sid, food.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, sid);
    }

    @Override
    public String toString() {
        return "Food{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sid='" + sid + '\'' +
                '}';
    }
}
