package com.amuyu.whattoeat.data.repo;


import com.amuyu.whattoeat.data.Local;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Situation;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class Repository implements DataSource {

    // local, remote, cache
    private final DataSource localDataSource;

    @Inject
    public Repository(@Local DataSource localRepository) {
        this.localDataSource = localRepository;
    }

    @Override
    public Flowable<List<Situation>> getSituations() {
        return localDataSource.getSituations();
    }

    @Override
    public Flowable<List<Food>> getFoods(String situationId) {
        return localDataSource.getFoods(situationId);
    }
}
