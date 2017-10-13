package com.example.tony.finalproject_plants.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.tony.finalproject_plants.R;

/**
 * Created by SHIYONG on 2017/10/13.
 * This is the main activity.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }
}
