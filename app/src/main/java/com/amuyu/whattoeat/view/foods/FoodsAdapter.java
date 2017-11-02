package com.amuyu.whattoeat.view.foods;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amuyu.whattoeat.R;
import com.amuyu.whattoeat.domain.model.Food;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.ViewHolder> {

    private List<Food> items = new ArrayList<>();
    private FoodsItemListener itemListener;

    public FoodsAdapter(FoodsItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.situation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Food food = items.get(position);
        holder.textView.setText(food.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemClick(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceData(List<Food> foods) {
        setList(foods);
        notifyDataSetChanged();
    }

    private void setList(List<Food> foods) {
        items = checkNotNull(foods);
    }

    public void clear() {
        items.clear();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public interface FoodsItemListener {
        void onItemClick(Food food);
    }
}
