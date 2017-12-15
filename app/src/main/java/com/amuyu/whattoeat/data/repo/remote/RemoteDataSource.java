package com.amuyu.whattoeat.data.repo.remote;


import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.data.repo.DataSource;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Group;
import com.amuyu.whattoeat.domain.model.Situation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

public class RemoteDataSource implements DataSource {

    public static RemoteDataSource INSTANCE;
    private DatabaseReference database;

    @Inject
    public RemoteDataSource() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new RemoteDataSource();
        return INSTANCE;
    }

    @Override
    public Flowable<List<Situation>> getSituations() {
        return Flowable.create(new FlowableOnSubscribe<List<Situation>>() {
            @Override
            public void subscribe(final FlowableEmitter<List<Situation>> e) throws Exception {
                database.child("situations").orderByChild("gid")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                List<Situation> situations = new ArrayList<>();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    situations.add(snapshot.getValue(Situation.class));
                                }
                                e.onNext(situations);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                e.onError(databaseError.toException());
                            }
                        });
            }
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<List<Food>> getFoods(final String situationId) {
        return Flowable.create(new FlowableOnSubscribe<List<Food>>() {
            @Override
            public void subscribe(final FlowableEmitter<List<Food>> e) throws Exception {
                database.child("foods").child(situationId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Food> foods = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Logger.d(snapshot.toString());
                            foods.add(snapshot.getValue(Food.class));
                        }
                        Logger.d(foods);
                        e.onNext(foods);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        e.onError(databaseError.toException());
                    }
                });
            }
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<List<Group>> getGroups() {
        return Flowable.create(new FlowableOnSubscribe<List<Group>>() {
            @Override
            public void subscribe(final FlowableEmitter<List<Group>> e) throws Exception {
                database.child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Group> groups = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            groups.add(snapshot.getValue(Group.class));
                        }
                        e.onNext(groups);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        e.onError(databaseError.toException());
                    }
                });
            }
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Situation getSituation(String id) {
        // Nothing to do.
        return null;
    }
}
