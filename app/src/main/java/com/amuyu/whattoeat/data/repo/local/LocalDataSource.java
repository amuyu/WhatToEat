package com.amuyu.whattoeat.data.repo.local;


import com.amuyu.whattoeat.data.File;
import com.amuyu.whattoeat.data.repo.DataSource;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Group;
import com.amuyu.whattoeat.domain.model.Situation;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

public class LocalDataSource implements DataSource {

    private LocalApi localApi;

    @Inject
    public LocalDataSource(@File LocalApi localApi) {
        this.localApi = localApi;
    }

    @Override
    public Flowable<List<Situation>> getSituations() {
        return Flowable.create(new FlowableOnSubscribe<List<Situation>>() {
            @Override
            public void subscribe(FlowableEmitter<List<Situation>> e) throws Exception {
                List<Situation> situations = localApi.getSituations();
                if (situations != null) {
                    e.onNext(situations);
                    e.onComplete();
                } else {
                    e.onError(new Throwable("no situation data"));
                }
            }
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<List<Food>> getFoods(final String situationId) {
        return Flowable.create(new FlowableOnSubscribe<List<Food>>() {
            @Override
            public void subscribe(FlowableEmitter<List<Food>> e) throws Exception {
                List<Food> foods = localApi.getFoods(situationId);
                if (foods != null) {
                    e.onNext(foods);
                    e.onComplete();
                } else {
                    e.onError(new Throwable("no food data"));
                }
            }
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<List<Group>> getGroups() {
        return Flowable.create(new FlowableOnSubscribe<List<Group>>() {
            @Override
            public void subscribe(FlowableEmitter<List<Group>> e) throws Exception {
                List<Group> groups = localApi.getGroups();
                if (groups != null) {
                    e.onNext(groups);
                    e.onComplete();
                } else {
                    e.onError(new Throwable("no group data"));
                }
            }
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Situation getSituation(String id) {
        return localApi.getSituation(id);
    }
}
