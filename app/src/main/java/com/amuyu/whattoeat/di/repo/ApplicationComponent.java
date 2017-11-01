package com.amuyu.whattoeat.di.repo;


import com.amuyu.whattoeat.data.repo.Repository;
import com.amuyu.whattoeat.di.situations.SituationComponent;
import com.amuyu.whattoeat.di.situations.SituationModule;

import dagger.Component;

@Component(modules = { ApplicationModule.class, RepositoryModule.class })
public interface ApplicationComponent {
    Repository getRepository();
    SituationComponent situationsComponent(SituationModule module);
}
