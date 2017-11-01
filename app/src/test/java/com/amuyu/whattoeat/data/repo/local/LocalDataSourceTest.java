package com.amuyu.whattoeat.data.repo.local;

import com.amuyu.whattoeat.domain.model.Food;
import com.amuyu.whattoeat.domain.model.Situation;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class LocalDataSourceTest {

    private static List<Situation> SITUATIONS = Lists.newArrayList(new Situation("1", "중국집"),
            new Situation("2", "카페"));

    private static List<Food> FOODS = Lists.newArrayList(new Food("11", "짜장면"),
            new Food("12", "짬뽕"));

    @Mock
    LocalApi localApi;

    private LocalDataSource localDataSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        localDataSource = new LocalDataSource(localApi);
    }

    @Test
    public void getSituations() throws Exception {
        setSituations(localApi, SITUATIONS);

        TestSubscriber<List<Situation>> testSubscriber = new TestSubscriber<>();
        localDataSource.getSituations().subscribe(testSubscriber);

        verify(localApi).getSituations();
        testSubscriber.assertValue(SITUATIONS);
    }

    @Test
    public void getFoods() throws Exception {
        setFoods(localApi, FOODS);

        TestSubscriber<List<Food>> testSubscriber = new TestSubscriber<>();

        final String situationId = "1";
        localDataSource.getFoods(situationId).subscribe(testSubscriber);

        verify(localApi).getFoods(situationId);
        testSubscriber.assertValue(FOODS);

    }

    private void setSituations(LocalApi localApi, List<Situation> situations) {
        when(localApi.getSituations()).thenReturn(situations);
    }

    private void setFoods(LocalApi localApi, List<Food> foods) {
        when(localApi.getFoods(anyString())).thenReturn(foods);
    }

}