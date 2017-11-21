package com.example.tony.finalproject_plants;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.List;


public class noteclass extends Fragment {
    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note, container, false);
        List<Plant> plantList = Plant.getAllPlants();
        int id[] = new int[5];
        id[0] = R.id.photinia;
        id[1] = R.id.planch;
        id[2] = R.id.jacq;
        id[3] = R.id.hance;
        id[4] = R.id.linn;
        int index = 0;
        for (final Plant p:plantList)
        {
            p.imgbtn = view.findViewById(id[index]);
            index += 1;  //Here is a big problem.
            p.imgbtn.setImageBitmap(getPhoto(p.getName()));
            p.imgbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(noteclass.this.getActivity(),plants.class);
                    intent.putExtra("name", p.getName());
                    startActivity(intent);
                }
            });
        }
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Bitmap getPhoto(String name){
        Plant plant = Plant.getPlantByName(name);
        FileUtils fu = new FileUtils(noteclass.this.getActivity().getApplicationContext());
        Bitmap img = fu.getPlantBitmap(plant);
        return img;
    }

    public int dip2px(float dpValue){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
