package com.example.tony.finalproject_plants;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.TextView;


import static com.example.tony.finalproject_plants.R.id.allplantslayout;

public class plants extends Activity {
    private String name;
    private String des;
    private TextView textview;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allplantslayout);
        textview = this.findViewById(allplantslayout);
        name = getIntent().getStringExtra("name");
        des = getDescription(name);
        textview.setText(des);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getDescription(String name){
        Plant plant = Plant.getPlantByName(name);
        FileUtils fu = new FileUtils(this.getApplicationContext());
        String des = fu.getPlantDescription(plant);
        return des;
    }
}

