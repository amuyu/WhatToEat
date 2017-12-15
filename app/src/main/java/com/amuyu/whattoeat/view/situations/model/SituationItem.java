package com.amuyu.whattoeat.view.situations.model;


import com.amuyu.whattoeat.domain.model.Situation;

public class SituationItem extends Situation implements SituationViewItem {

    public SituationItem(String id, String name, String gid) {
        super(id, name, gid);
    }
}
