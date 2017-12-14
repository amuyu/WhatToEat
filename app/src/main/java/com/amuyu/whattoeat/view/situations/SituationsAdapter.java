package com.amuyu.whattoeat.view.situations;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amuyu.whattoeat.R;
import com.amuyu.whattoeat.view.model.GroupItem;
import com.amuyu.whattoeat.view.model.SituationItem;
import com.amuyu.whattoeat.view.model.SituationViewItem;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SituationsAdapter extends RecyclerView.Adapter<SituationsAdapter.ViewHolder> {

    private List<SituationViewItem> items = new ArrayList<>();
    private SituationItemListener itemListener;

    private final int HEADER_TYPE = 0;
    private final int ITEM_TYPE = 1;

    public SituationsAdapter(SituationItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public int getItemViewType(int position) {
        SituationViewItem item = getItem(position);
        if (item instanceof GroupItem)
            return HEADER_TYPE;
        else if (item instanceof SituationItem)
            return ITEM_TYPE;

        return ITEM_TYPE;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
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
        final SituationViewItem situation = items.get(position);
        if (situation instanceof GroupItem) {
            GroupItem item = (GroupItem)situation;
            holder.textView.setText(item.getName());
        } else if (situation instanceof SituationItem) {
            final SituationItem item = (SituationItem)situation;
            holder.textView.setText(item.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public SituationViewItem getItem(int position) {
        return items.get(position);
    }

    public void replaceData(List<SituationViewItem> situations) {
        setList(situations);
        notifyDataSetChanged();
    }

    private void setList(List<SituationViewItem> situations) {
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
        void onItemClick(SituationItem situation);
    }
}
