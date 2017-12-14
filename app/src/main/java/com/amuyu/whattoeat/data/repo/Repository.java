package com.amuyu.whattoeat.data.repo;


import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.data.Remote;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Group;
import com.amuyu.whattoeat.domain.model.Situation;
import com.amuyu.whattoeat.view.model.GroupItem;
import com.amuyu.whattoeat.view.model.SituationItem;
import com.amuyu.whattoeat.view.model.SituationViewItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Repository implements DataSource {

    // local, remote, cache
    private final DataSource dataSource;

    private HashMap<String, Group> mCacheGroups;
    private HashMap<String, Situation> mCacheSituations;
    private HashMap<String, Food> mCacheFoods;

    private boolean isCacheDirty = false;
    private String mSidFoods;

    @Inject
    public Repository(@Remote DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void refresh() {
        isCacheDirty = true;
    }

    public Flowable<List<SituationViewItem>> getSituationsForView() {
        Logger.d("");
        return Flowable.combineLatest(
                getSituations(),
                getGroups(),
                new BiFunction<List<Situation>, List<Group>, List<SituationViewItem>>() {
                    @Override
                    public List<SituationViewItem> apply(List<Situation> situations, List<Group> groups) throws Exception {
                        Logger.d("");
                        refreshCacheGroups(groups);
                        refreshCacheSituations(situations);

                        return getSituationViewList(situations);
                    }
                })
                .subscribeOn(Schedulers.computation());
    }


    @Override
    public Flowable<List<Situation>> getSituations() {
        Logger.d("");
        if (mCacheSituations != null && !isCacheDirty) {
            return Flowable.create(new FlowableOnSubscribe<List<Situation>>() {
                @Override
                public void subscribe(FlowableEmitter<List<Situation>> e) throws Exception {
                    e.onNext(new ArrayList<Situation>(mCacheSituations.values()));
                }
            }, BackpressureStrategy.LATEST);
        }

        return dataSource.getSituations();
    }

    @Override
    public Flowable<List<Food>> getFoods(final String situationId) {
        Logger.d("");
        if (mSidFoods != null && !mSidFoods.equals(situationId)) isCacheDirty = true;

        if (mCacheFoods != null && !isCacheDirty) {
            return Flowable.create(new FlowableOnSubscribe<List<Food>>() {
                @Override
                public void subscribe(FlowableEmitter<List<Food>> e) throws Exception {
                    e.onNext(new ArrayList<Food>(mCacheFoods.values()));
                }
            }, BackpressureStrategy.LATEST);
        }

        return dataSource.getFoods(situationId)
                .doOnNext(new Consumer<List<Food>>() {
                    @Override
                    public void accept(List<Food> foods) throws Exception {
                        refreshCacheFoods(foods, situationId);
                    }
                })
                .subscribeOn(Schedulers.computation());
    }

    @Override
    public Flowable<List<Group>> getGroups() {
        Logger.d("");
        return dataSource.getGroups();
    }

    private void initCache() {
        if (mCacheGroups == null) mCacheGroups = new LinkedHashMap<>();
        if (mCacheSituations == null) mCacheSituations = new LinkedHashMap<>();
        if (mCacheFoods == null) mCacheFoods = new LinkedHashMap<>();
    }

    private void refreshCacheGroups(List<Group> groups) {
        initCache();
        mCacheGroups.clear();
        for (Group group : groups) {
            mCacheGroups.put(group.getId(), group);
        }
        isCacheDirty = false;
    }

    private void refreshCacheSituations(List<Situation> situations) {
        initCache();
        mCacheSituations.clear();
        for (Situation situation : situations) {
            mCacheSituations.put(situation.getId(), situation);
        }
        isCacheDirty = false;
    }

    private void refreshCacheFoods(List<Food> foods, String sid) {
        initCache();
        mCacheFoods.clear();
        for (Food food : foods) {
            mCacheFoods.put(food.getId(), food);
        }
        mSidFoods = sid;
        isCacheDirty = false;
    }

    private List<SituationViewItem> getSituationViewList(List<Situation> situations) {
        Logger.d(situations);
        String gid = situations.get(0).getGid();
        List<SituationViewItem> items = new ArrayList<>();
        Group group = mCacheGroups.get(gid);

        if (group != null) items.add(new GroupItem(group.getId(), group.getName()));
        for (Situation s : situations) {
            if (!gid.equals(s.getGid())) {
                gid = s.getGid();
                group = mCacheGroups.get(gid);
                if (group != null) items.add(new GroupItem(group.getId(), group.getName()));
            }
            items.add(new SituationItem(s.getId(), s.getName(), s.getId()));
        }

        Logger.d(items);
        return items;
    }
}
