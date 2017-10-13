package com.example.tony.finalproject_plants.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.tony.finalproject_plants.R;

/**
 * Created by SHIYONG on 2017/10/13.
 * This activity is used to display the infomation of a plant.
 */

public class PlantActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_plant);
    }
}
