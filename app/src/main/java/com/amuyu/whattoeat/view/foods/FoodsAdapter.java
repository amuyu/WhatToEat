package com.amuyu.whattoeat.view.foods;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amuyu.whattoeat.R;
import com.amuyu.whattoeat.domain.model.Food;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.ViewHolder> {

    private List<Food> items = new ArrayList<>();
    private FoodItemListener itemListener;

    public FoodsAdapter(FoodItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Food food = items.get(position);
        holder.textView.setText(food.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemView.setSelected(true);
                itemListener.onItemClick(food);
            }
        });
        itemListener.onDrawImage(holder.imageView, food.getId());
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
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.image);
//            imageView.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        }
    }

}
