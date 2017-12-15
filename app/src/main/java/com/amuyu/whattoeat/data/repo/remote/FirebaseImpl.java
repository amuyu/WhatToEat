package com.amuyu.whattoeat.data.repo.remote;


import com.amuyu.whattoeat.infra.FireBaseRepo;
import com.amuyu.whattoeat.infra.GlideRequest;
import com.amuyu.whattoeat.infra.GlideRequests;
import com.google.firebase.storage.StorageReference;

public class FirebaseImpl implements IFirebaseRepo {

    private FireBaseRepo mFireBaseRepo;

    public FirebaseImpl() {
        mFireBaseRepo = new FireBaseRepo();
    }

    public GlideRequest loadImage(GlideRequests requests, String childName) {
        StorageReference child = mFireBaseRepo.getChildRef(childName);
        return requests.load(child);
    }
}
