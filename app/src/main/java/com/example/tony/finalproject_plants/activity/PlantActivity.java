package com.example.tony.finalproject_plants.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tony.finalproject_plants.R;
import com.example.tony.finalproject_plants.model.Plant;

/**
 * Created by SHIYONG on 2017/10/13.
 * This activity is used to display the infomation of a plant.
 */

public class PlantActivity extends Activity {
    private ImageView plant_image;
    private TextView  plant_name;
    private TextView long_info;
    private Bundle plant;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent=getIntent();
        plant=intent.getBundleExtra("plant_bundle");
        setContentView(R.layout.activity_plant);
        initView();
    }
    public void initView(){
        plant_image=(ImageView)findViewById(R.id.P_plantImage);
        plant_name=(TextView)findViewById(R.id.P_plantName);
        long_info=(TextView)findViewById(R.id.P_plantContent);
        plant_name.setText(plant.getString("plant_name","无"));
        plant_image.setImageResource(plant.getInt("image_id",R.drawable.total));
    }
}
