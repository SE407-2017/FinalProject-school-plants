package com.example.tony.finalproject_plants;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class noteclass extends Fragment {
    private ImageButton phobtn;
    private ImageButton plabtn;
    private ImageButton jacbtn;
    private ImageButton hanbtn;
    private ImageButton linbtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note, container, false);
        phobtn = view.findViewById(R.id.photinia);
        phobtn.setImageBitmap(getPhoto("photinia"));
        plabtn = view.findViewById(R.id.planch);
        plabtn.setImageBitmap(getPhoto("planch"));
        jacbtn = view.findViewById(R.id.jacq);
        jacbtn.setImageBitmap(getPhoto("jacq"));
        hanbtn = view.findViewById(R.id.hance);
        hanbtn.setImageBitmap(getPhoto("hance"));
        linbtn = view.findViewById(R.id.linn);
        linbtn.setImageBitmap(getPhoto("linn"));
        phobtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(noteclass.this.getActivity(),plants.class);
                intent.setFlags(1);
                intent.putExtra("name", "photinia");
                startActivity(intent);
            }
        });
        plabtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(noteclass.this.getActivity(),plants.class);
                intent.setFlags(2);
                intent.putExtra("name", "planch");
                startActivity(intent);
            }
        });
        jacbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(noteclass.this.getActivity(),plants.class);
                intent.setFlags(3);
                intent.putExtra("name", "jacq");
                startActivity(intent);
            }
        });
        hanbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(noteclass.this.getActivity(),plants.class);
                intent.setFlags(4);
                intent.putExtra("name", "hance");
                startActivity(intent);
            }
        });
        linbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(noteclass.this.getActivity(),plants.class);
                intent.setFlags(5);
                intent.putExtra("name", "linn");
                startActivity(intent);
            }
        });
        return view;
    }
    private Bitmap getPhoto(String name){
        Plant plant = Plant.getPlantByName(name);
        FileUtils fu = new FileUtils(noteclass.this.getActivity().getApplicationContext());
        Bitmap img = fu.getPlantBitmap(plant);
        return img;
    }
}
