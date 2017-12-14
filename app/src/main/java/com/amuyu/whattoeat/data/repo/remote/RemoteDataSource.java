package com.amuyu.whattoeat.data.repo.remote;


import com.amuyu.whattoeat.data.repo.DataSource;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Situation;

import java.util.List;

import io.reactivex.Flowable;

public class RemoteDataSource implements DataSource {




    @Override
    public Flowable<List<Situation>> getSituations() {
        return null;
    }

    @Override
    public Flowable<List<Food>> getFoods(String situationId) {
        return null;
    }
}
