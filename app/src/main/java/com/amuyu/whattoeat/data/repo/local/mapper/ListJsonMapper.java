package com.amuyu.whattoeat.data.repo.local.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amuyu on 2017. 10. 31..
 */

public class ListJsonMapper<T> {

    private final Gson gson;
    private final Class<T> clazz;

    public ListJsonMapper(Class<T> clazz) {
        this.clazz = clazz;
        gson = new Gson();
    }

    public List<T> transFormSituations(String json) {


        Type type = TypeToken.getParameterized(ArrayList.class, clazz).getType();

//        Type type = new TypeToken<List<T>>() {
//        }.getType();
        try {
            List<T> situations = gson.fromJson(json, type);
            return situations;
        } catch (JsonSyntaxException e) {
            throw e;
        }
    }
}
