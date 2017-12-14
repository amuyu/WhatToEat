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
    private SituationItemListener itemListener;

    public SituationsAdapter(SituationItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 1;
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.situation_header_item, parent, false);
            return new ViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.situation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Situation situation = items.get(position);
        holder.textView.setText(situation.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemClick(situation);
            }
        });
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

    public interface SituationItemListener {
        void onItemClick(Situation situation);
    }
}
