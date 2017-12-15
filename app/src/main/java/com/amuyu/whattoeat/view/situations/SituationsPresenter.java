package com.amuyu.whattoeat.view.situations;


import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.data.repo.Repository;
import com.amuyu.whattoeat.view.situations.model.SituationItem;
import com.amuyu.whattoeat.view.situations.model.SituationViewItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class SituationsPresenter implements SituationsContract.Presenter {

    private final SituationsContract.View view;
    private final Repository repository;

    @Inject
    public SituationsPresenter(SituationsContract.View view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }


    @Override
    public void initialize() {
        loadSituations();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void loadSituations() {
        repository.getSituationsForView()
                .subscribe(new Consumer<List<SituationViewItem>>() {
                    @Override
                    public void accept(List<SituationViewItem> situations) throws Exception {
                        view.showSituations(situations);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(""+throwable.getMessage());
                    }
                });
    }



    @Override
    public void openSituationFoods(SituationItem situation) {
        view.showFoodsUi(situation.getId());
    }
}
