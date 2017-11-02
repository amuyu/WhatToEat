package com.amuyu.whattoeat.view.foods;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.R;
import com.amuyu.whattoeat.di.foods.FoodsModule;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.view.SampleActivity;
import com.amuyu.whattoeat.view.WApplication;

import java.util.List;

import javax.inject.Inject;

public class FoodsFragment extends Fragment implements FoodsContract.View {

    private FoodsAdapter listAdapter;
    @Inject public FoodsContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("");
        listAdapter = new FoodsAdapter(itemListener);
        initializeDagger();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.foods_frag, container, false);

        RecyclerView listView = (RecyclerView)view.findViewById(R.id.list);
        listView.setAdapter(listAdapter);
        listView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        Button button = (Button)view.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SampleActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.initialize();
    }

    @Override
    public void initializeDagger() {
        String situationId = "1";
        WApplication app = (WApplication)getActivity().getApplication();
        app.getApplicationComponent()
                .foodsComponent(new FoodsModule(this, situationId))
                .inject(this);
    }

    @Override
    public void setPresenter(FoodsContract.Presenter presenter) {

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
    public void showFoods(List<Food> foods) {
        Logger.d(""+foods);
        listAdapter.replaceData(foods);
    }

    FoodsAdapter.FoodsItemListener itemListener = new FoodsAdapter.FoodsItemListener() {
        @Override
        public void onItemClick(Food food) {
            //
        }
    };
}
