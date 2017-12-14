package com.amuyu.whattoeat.domain.model;


import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Group {
    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public Group() {
    }

    public Group(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Group(@NonNull String id, String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equal(id, group.id) &&
                Objects.equal(name, group.name);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
