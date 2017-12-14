package com.amuyu.whattoeat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.amuyu.logger.Logger;
import com.amuyu.whattoeat.util.ActivityUtils;
import com.amuyu.whattoeat.view.situations.SituationsFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = new SituationsFragment();
            moveFragment(fragment, false);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public void moveFragment(Fragment fragment, boolean addToBackStack) {
        ActivityUtils.FragmentOperation operation = ActivityUtils.FragmentOperation.create(fragment.getClass());
        operation.arguments(fragment.getArguments());
        if (fragment.getArguments() != null)
            Logger.d("bundle:"+fragment.getArguments().toString());
        operation.addToBackStack(addToBackStack);
        ActivityUtils.replaceFragment(this, getSupportFragmentManager(),
                operation, R.id.contentFrame);

//        ActivityUtils.addFragmentToActivity(
//                getSupportFragmentManager(), fragment, R.id.contentFrame);
    }



}
