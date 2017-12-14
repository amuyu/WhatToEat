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
import android.widget.ImageView;
import android.widget.Toast;

import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.R;
import com.amuyu.whattoeat.data.repo.remote.IFirebaseRepo;
import com.amuyu.whattoeat.di.foods.FoodsModule;
import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.infra.GlideApp;
import com.amuyu.whattoeat.view.SampleActivity;
import com.amuyu.whattoeat.view.WApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.amuyu.whattoeat.view.SampleActivity.FOOD_LIST;

public class FoodsFragment extends Fragment implements FoodsContract.View, FoodItemListener {

    public static final String SITUATION_ID = "situation_id";

    private FoodsAdapter listAdapter;
    @Inject public FoodsContract.Presenter presenter;
    @Inject public IFirebaseRepo repo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("");
        listAdapter = new FoodsAdapter(this);
        initializeDagger();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.foods_frag, container, false);

        RecyclerView listView = (RecyclerView)view.findViewById(R.id.list);
        listView.setAdapter(listAdapter);
        listView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        Button button = (Button)view.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.ask();
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
        WApplication app = (WApplication)getActivity().getApplication();
        app.getApplicationComponent()
                .foodsComponent(new FoodsModule(this, getArguments().getString(SITUATION_ID)))
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

    @Override
    public void showRequest(ArrayList<Food> selectedFood) {
        Logger.d(selectedFood);
        Intent intent = new Intent(getActivity(), SampleActivity.class);
        intent.putExtra(FOOD_LIST, selectedFood);
        startActivity(intent);
    }

    @Override
    public void showOverSelectedFood() {
        Toast.makeText(getActivity(), "음식은 4개만!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Food food, boolean selected) {
        presenter.selectFood(food, selected);
    }

    @Override
    public void onDrawImage(final ImageView imageView, final String name) {
        repo.loadImage(GlideApp.with(this), name)
                .centerCrop()
                .into(imageView);
    }
}
