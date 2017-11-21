package com.amuyu.whattoeat.view.situations;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.MainActivity;
import com.amuyu.whattoeat.R;
import com.amuyu.whattoeat.di.situations.SituationModule;
import com.amuyu.whattoeat.domain.model.Situation;
import com.amuyu.whattoeat.view.WApplication;
import com.amuyu.whattoeat.view.foods.FoodsFragment;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;

import java.util.List;

import javax.inject.Inject;

public class SituationsFragment extends Fragment implements SituationsContract.View {

    private SituationsAdapter listAdpater;
    @Inject public SituationsContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listAdpater = new SituationsAdapter(itemListener);
        initializeDagger();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.situation_frag, container, false);

        RecyclerView listView = (RecyclerView)view.findViewById(R.id.list);
        listView.setAdapter(listAdpater);

        ChipsLayoutManager layoutManager = ChipsLayoutManager.newBuilder(getContext())
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setMaxViewsInRow(3)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_CENTER_DENSE)
                .build();

        listView.addItemDecoration(new SpacingItemDecoration(getResources().getDimensionPixelOffset(R.dimen.item_space),
                getResources().getDimensionPixelOffset(R.dimen.item_space)));

        listView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.initialize();
    }

    @Override
    public void setPresenter(SituationsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading() {
        // nothing
    }

    @Override
    public void hideLoading() {
        // nothing
    }

    @Override
    public void initializeDagger() {
        WApplication app = (WApplication)getActivity().getApplication();
        app.getApplicationComponent()
                .situationsComponent(new SituationModule(this))
                .inject(this);
    }

    @Override
    public void showSituations(List<Situation> situations) {
        Logger.d("");
        listAdpater.replaceData(situations);
    }

    @Override
    public void showFoodsUi(String situationId) {
        Logger.d("");
        Fragment fragment = new FoodsFragment();
        ((MainActivity)getActivity()).moveFragment(fragment);
    }

    SituationsAdapter.SituationItemListener itemListener = new SituationsAdapter.SituationItemListener() {
        @Override
        public void onItemClick(Situation situation) {
            presenter.openSituationFoods(situation);
        }
    };
}
