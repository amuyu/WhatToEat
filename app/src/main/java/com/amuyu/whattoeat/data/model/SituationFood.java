package com.amuyu.whattoeat.data.model;


import com.amuyu.whattoeat.domain.model.Food;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SituationFood {

    @SerializedName("situation_id") private String situationId;
    @SerializedName("foods") private List<Food> foods;

    public String getSituationId() {
        return situationId;
    }

    public void setSituationId(String situationId) {
        this.situationId = situationId;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
