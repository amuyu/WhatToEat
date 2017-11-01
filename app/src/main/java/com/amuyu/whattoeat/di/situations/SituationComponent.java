package com.amuyu.whattoeat.di.situations;


import com.amuyu.whattoeat.view.situations.SituationsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = SituationModule.class)
public interface SituationComponent {
    void inject(SituationsFragment fragment);
}
