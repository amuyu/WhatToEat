package com.amuyu.whattoeat.view.random;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.amuyu.whattoeat.R;

public class RandomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        final TextView textView = (TextView)findViewById(R.id.text);
        WheelView wheelView = (WheelView)findViewById(R.id.wheel);
        wheelView.setCallback(new WheelView.Listener() {
            @Override
            public void onSelectedItem(String item) {
                if (item != null) textView.setText(item);
                else textView.setText("...");
            }
        });
    }
}
