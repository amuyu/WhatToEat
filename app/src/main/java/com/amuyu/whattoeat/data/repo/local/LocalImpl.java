package com.amuyu.whattoeat.data.repo.local;


import android.content.Context;

import com.amuyu.whattoeat.data.model.SituationFood;
import com.amuyu.whattoeat.data.repo.local.mapper.ListJsonMapper;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Situation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class LocalImpl implements LocalApi {

    private final Context context;
    private List<Situation> situations;
    private HashMap<String, SituationFood> situationFoodMap;

    public LocalImpl(Context context) {
        this.context = context;
        loadData();
    }

    @Override
    public List<Situation> getSituations() {
        return situations;
    }

    @Override
    public List<Food> getFoods(String situationId) {
        SituationFood s = situationFoodMap.get(situationId);
        return (s != null)? s.getFoods() : null;
    }

    private void loadData() {
        loadSituations();
        loadSituationFoods();
    }

    private void loadSituations() {
        situations = new ListJsonMapper<>(Situation.class).
                transFormSituations(getResponseFromLocalJson("situation_data.json"));

    }

    private void loadSituationFoods() {
        if (situationFoodMap == null) {
            situationFoodMap = new LinkedHashMap<>();
        }

        List<SituationFood> situationFoods = new ListJsonMapper<>(SituationFood.class).
                transFormSituations(getResponseFromLocalJson("food_data.json"));

        for(int i = 0; i < situationFoods.size(); i++) {
            SituationFood s = situationFoods.get(i);
            situationFoodMap.put(s.getSituationId(), s);
        }
    }

    // situation_data.json
    // food_data.json
    private String getResponseFromLocalJson(String fileName) {
        String str = "";
        try {
            StringBuilder builder = new StringBuilder();
            InputStream json = context.getAssets().open(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(json));

            while ((str = in.readLine()) != null) {
                builder.append(str);
            }
            in.close();
            str = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
