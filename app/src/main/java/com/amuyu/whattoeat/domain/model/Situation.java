package com.amuyu.whattoeat.domain.model;


import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public final class Situation {
    @NonNull
    @SerializedName("id")
    private final String mId;

    @NonNull
    @SerializedName("name")
    private final String mName;

    public Situation(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Situation(String id, String name) {
        this.mId = id;
        this.mName = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Situation situation = (Situation) obj;
        return Objects.equal(mId, situation.mId) &&
                Objects.equal(mName, situation.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId,mName);
    }
}
