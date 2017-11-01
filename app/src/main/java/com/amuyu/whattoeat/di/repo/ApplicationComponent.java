package com.amuyu.whattoeat.di.repo;


import com.amuyu.whattoeat.data.repo.Repository;

import dagger.Component;

@Component(modules = { ApplicationModule.class, RepositoryModule.class })
public interface ApplicationComponent {
    Repository getRepository();
}
