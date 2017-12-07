package com.amuyu.whattoeat.di.repo;


import com.amuyu.whattoeat.data.repo.remote.FirebaseImpl;
import com.amuyu.whattoeat.data.repo.remote.IFirebaseRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    IFirebaseRepo provideStorage()  {
        return new FirebaseImpl();
    }
}
