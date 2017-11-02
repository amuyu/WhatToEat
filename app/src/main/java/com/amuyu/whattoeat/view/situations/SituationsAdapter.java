package com.amuyu.whattoeat.view.situations;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amuyu.whattoeat.R;
import com.amuyu.whattoeat.domain.model.Situation;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SituationsAdapter extends RecyclerView.Adapter<SituationsAdapter.ViewHolder> {

    private List<Situation> items = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.situation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Situation situation = items.get(position);
        holder.textView.setText(situation.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceData(List<Situation> situations) {
        setList(situations);
        notifyDataSetChanged();
    }

    private void setList(List<Situation> situations) {
        items = checkNotNull(situations);
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
}