package com.amuyu.whattoeat.data.repo.local;


import com.amuyu.whattoeat.data.repo.DataSource;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Situation;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

public class LocalDataSource implements DataSource {

    private LocalApi localApi;

    public LocalDataSource(LocalApi localApi) {
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
}
