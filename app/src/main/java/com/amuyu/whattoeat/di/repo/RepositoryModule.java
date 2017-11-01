package com.amuyu.whattoeat.di.repo;


import com.amuyu.whattoeat.data.File;
import com.amuyu.whattoeat.data.Local;
import com.amuyu.whattoeat.data.repo.DataSource;
import com.amuyu.whattoeat.data.repo.local.LocalApi;
import com.amuyu.whattoeat.data.repo.local.LocalDataSource;
import com.amuyu.whattoeat.data.repo.local.LocalImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Binds
    @File
    abstract LocalApi provideLocalFile(LocalImpl local);


    @Binds
    @Local
    abstract DataSource provideLocalDataSource(LocalDataSource localDataSource);
}
