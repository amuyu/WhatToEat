package com.amuyu.whattoeat.view.situations;


import com.amuyu.whattoeat.view.BasePresenter;
import com.amuyu.whattoeat.view.BaseView;
import com.amuyu.whattoeat.view.model.SituationItem;
import com.amuyu.whattoeat.view.model.SituationViewItem;

import java.util.List;

public interface SituationsContract {
    interface View extends BaseView<Presenter> {
        void showSituations(List<SituationViewItem> situations);
        void showFoodsUi(String situationId);
    }

    interface Presenter extends BasePresenter {
        void loadSituations();
        void openSituationFoods(SituationItem situation);
    }
}
