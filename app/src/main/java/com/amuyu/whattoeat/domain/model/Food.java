package com.amuyu.whattoeat.domain.model;


import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public final class Food {
    @NonNull
    @SerializedName("food_id")
    private final String mId;

    @SerializedName("name")
    private final String mName;

    public Food(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Food(String id, String name) {
        this.mId = id;
        this.mName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equal(mId, food.mId) &&
                Objects.equal(mName, food.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId,mName);
    }
}
