package com.amuyu.whattoeat.domain.model;


import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Situation {
    @NonNull
    @SerializedName("id")
    private String id;

    @NonNull
    @SerializedName("name")
    private String name;

    @NonNull
    @SerializedName("gid")
    private String gid;

    public Situation() {
    }

    public Situation(String name, String gid) {
        this(UUID.randomUUID().toString(), name, gid);
    }

    public Situation(String id, String name, String gid) {
        this.id = id;
        this.name = name;
        this.gid = gid;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGid() {
        return gid;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Situation situation = (Situation) obj;
        return Objects.equal(id, situation.id) &&
                Objects.equal(name, situation.name) &&
                Objects.equal(gid, situation.gid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, gid);
    }

    @Override
    public String toString() {
        return "Situation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gid='" + gid + '\'' +
                '}';
    }
}
