package com.amuyu.whattoeat.data.repo.local;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Situation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocalImplTest {


    private Context context;
    private LocalApi localApi;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        localApi = new LocalImpl(context);
    }

    @Test
    public void getSituations() throws Exception {
        List<Situation> situations = localApi.getSituations();

        final Situation situationOne = situations.get(0);
        final Situation situationTwo = situations.get(1);
        final Situation situationThree = situations.get(2);

        assertThat(situationOne.getName(), is("중국집"));
        assertThat(situationTwo.getName(), is("카페"));
        assertThat(situationThree.getName(), is("배달"));
    }

    @Test
    public void getFoods() throws Exception {
        final String situationId = "1";
        List<Food> foods = localApi.getFoods(situationId);

        final Food foodOne = foods.get(0);
        final Food foodTwo = foods.get(1);
        final Food foodThree = foods.get(2);
        final Food foodFour = foods.get(3);

        assertThat(foodOne.getName(), is("자장면"));
        assertThat(foodTwo.getName(), is("짬뽕"));
        assertThat(foodThree.getName(), is("탕수육"));
        assertThat(foodFour.getName(), is("깐풍기"));
    }

}