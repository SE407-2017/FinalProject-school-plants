package com.example.tony.finalproject_plants;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class results extends Activity {
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        String inp = getIntent().getStringExtra("name");
        Plant respla = Plant.getPlantByName(inp);
        TextView name = findViewById(R.id.plantsName);
        TextView description = findViewById(R.id.plantsDescriptions);
        description.setMovementMethod(ScrollingMovementMethod.getInstance());
        description.setText(getDescription(respla.getName()));
        name.setText(inp);
        ImageView plapic = findViewById(R.id.plantsImage);
        plapic.setImageBitmap(getPhoto(respla.getName()));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getDescription(String name){
        Plant plant = Plant.getPlantByName(name);
        FileUtils fu = new FileUtils(this.getApplicationContext());
        String des = fu.getPlantDescription(plant);
        return des;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Bitmap getPhoto(String name){
        Plant plant = Plant.getPlantByName(name);
        FileUtils fu = new FileUtils(results.this.getApplicationContext());
        Bitmap img = fu.getPlantBitmap(plant);
        return img;
    }
}
