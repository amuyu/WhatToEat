package com.amuyu.whattoeat.data.repo;


import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Group;
import com.amuyu.whattoeat.domain.model.Situation;

import java.util.List;

import io.reactivex.Flowable;

public interface DataSource {

    Flowable<List<Situation>> getSituations();

    Flowable<List<Food>> getFoods(final String situationId);

    Flowable<List<Group>> getGroups();

    Situation getSituation(String id);
}
