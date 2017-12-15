package com.amuyu.whattoeat.view.situations.model;


import android.support.annotation.NonNull;

import com.amuyu.whattoeat.domain.model.Group;

public class GroupItem extends Group implements SituationViewItem {

    public GroupItem(@NonNull String id, String name) {
        super(id, name);
    }
}
