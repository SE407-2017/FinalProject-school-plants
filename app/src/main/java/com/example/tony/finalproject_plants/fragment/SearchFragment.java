package com.example.tony.finalproject_plants.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tony.finalproject_plants.R;
import com.example.tony.finalproject_plants.activity.PlantActivity;
import com.example.tony.finalproject_plants.database.PlantDatabase;
import com.example.tony.finalproject_plants.model.Plant;

/**
 * Created by SHIYONG on 2017/10/13.
 */

public class SearchFragment extends Fragment {
    private EditText editText;
    private Button button;
    private PlantDatabase database;
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View searchView=layoutInflater.inflate(R.layout.fragment_search,null);
        editText=(EditText) searchView.findViewById(R.id.searched_plant);
        button=(Button)searchView.findViewById(R.id.search_button);
        database=PlantDatabase.getPlantDatabase(getActivity());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plant_name=editText.getText().toString();
                Plant plant=null;
                if(plant_name.length()==0){
                    Toast.makeText(getActivity(),"请输入内容！",Toast.LENGTH_SHORT).show();
                    return;
                } else plant=database.queryPlant(plant_name);
                //查找结束
                if(plant==null){
                    Toast.makeText(getActivity(),"未找到该植物！",Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    Intent intent=new Intent(getActivity(), PlantActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("plant_name",plant.getPlant_name());
                    bundle.putInt("image_id",plant.getImage_id());
                    intent.putExtra("plant_bundle",bundle);
                    startActivity(intent);
                }
            }
        });
        return searchView;
    }

}
