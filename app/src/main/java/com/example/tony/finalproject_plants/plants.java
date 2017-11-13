package com.example.tony.finalproject_plants;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.tony.finalproject_plants.R.id.han1;
import static com.example.tony.finalproject_plants.R.id.jac1;
import static com.example.tony.finalproject_plants.R.id.lin1;
import static com.example.tony.finalproject_plants.R.id.pho1;
import static com.example.tony.finalproject_plants.R.id.pla1;
public class plants extends Activity {
    private int type;
    private String name;
    private String des;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getFlags();
        switch (type) {
            case 1:
            case 6:
                setContentView(R.layout.photinia);
                textview = this.findViewById(pho1);
                name = getIntent().getStringExtra("name");
                des = getDescription(name);
                textview.setText(des);
                break;
            case 2:
                setContentView(R.layout.planch);
                textview = this.findViewById(pla1);
                name = getIntent().getStringExtra("name");
                des = getDescription(name);
                textview.setText(des);
                break;
            case 3:
                setContentView(R.layout.jacq);
                textview = this.findViewById(jac1);
                name = getIntent().getStringExtra("name");
                des = getDescription(name);
                textview.setText(des);
                break;
            case 4:
                setContentView(R.layout.hance);
                textview = this.findViewById(han1);
                name = getIntent().getStringExtra("name");
                des = getDescription(name);
                textview.setText(des);
                break;
            case 5:
                setContentView(R.layout.linn);
                textview = this.findViewById(lin1);
                name = getIntent().getStringExtra("name");
                des = getDescription(name);
                textview.setText(des);
                break;
            default:
                break;
        }
    }
    private String getDescription(String name){
        Plant plant = Plant.getPlantByName(name);
        FileUtils fu = new FileUtils(this.getApplicationContext());
        String des = fu.getPlantDescription(plant);
        return des;
    }
}

