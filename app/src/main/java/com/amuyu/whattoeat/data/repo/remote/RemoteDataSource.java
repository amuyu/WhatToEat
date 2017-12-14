package com.amuyu.whattoeat.data.repo.remote;


import com.amuyu.whattoeat.data.repo.DataSource;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Group;
import com.amuyu.whattoeat.domain.model.Situation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.reactivex.Flowable;

public class RemoteDataSource implements DataSource {

    private DatabaseReference database;

    public RemoteDataSource() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public Flowable<List<Situation>> getSituations() {
        return null;
    }

    @Override
    public Flowable<List<Food>> getFoods(String situationId) {
        return null;
    }

    @Override
    public Flowable<List<Group>> getGroups() {
        return null;
    }
}
