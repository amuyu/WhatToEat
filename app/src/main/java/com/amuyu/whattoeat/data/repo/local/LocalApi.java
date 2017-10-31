package com.amuyu.whattoeat.data.repo.local;


import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Situation;

import java.util.List;

public interface LocalApi {
    List<Situation> getSituations();
    List<Food> getFoods(String situationId);
}
