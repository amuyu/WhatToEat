package com.amuyu.whattoeat.view.situations;


import com.amuyu.whattoeat.domain.model.Situation;
import com.amuyu.whattoeat.view.BasePresenter;
import com.amuyu.whattoeat.view.BaseView;

import java.util.List;

public interface SituationsContract {
    interface View extends BaseView<Presenter> {
        void initializeDagger();
        void showSituations(List<Situation> situations);
    }

    interface Presenter extends BasePresenter {
        void loadSituations();
    }
}
