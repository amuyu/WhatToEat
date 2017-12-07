package com.amuyu.whattoeat.infra;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FireBaseRepo {

    StorageReference mStorageReference;

    public FireBaseRepo() {
        mStorageReference = FirebaseStorage.getInstance().getReference();
    }

    public StorageReference getChildRef(String childName) {
        return mStorageReference.child(childName);
    }

}
