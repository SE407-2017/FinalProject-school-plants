package com.example.tony.finalproject_plants;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class noteClass extends Fragment {

    @RequiresApi (api = Build.VERSION_CODES.KITKAT)
    public Bitmap getPhoto(String name){
        Plant plant = Plant.getPlantByName(name);
        FileUtils fu = new FileUtils(noteClass.this.getActivity().getApplicationContext());
        Bitmap img = fu.getPlantBitmap(plant);
        return img;
    }

    private class PlantAdapter extends ArrayAdapter<Plant> {
        private int resourceId;

        public PlantAdapter(Context context, int textViewResourceId, List<Plant> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Plant listPlants = getItem(position);
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.plantImage = (ImageView) view.findViewById(R.id.plant_image);
                viewHolder.plantName = (TextView) view.findViewById(R.id.plant_name);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.plantImage.setImageBitmap(getPhoto(listPlants.getName()));
            viewHolder.plantName.setText(listPlants.getName());
            return view;
        }

        class ViewHolder{
            ImageView plantImage;
            TextView plantName;
        }
    }

    private List<Plant> listViewPlants = new ArrayList<>();
    private List<Plant> plantsList = Plant.getAllPlants();

    @Nullable
    @Override
    @RequiresApi (api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note, container, false);
        initlist_view_plants();

        PlantAdapter adapter = new PlantAdapter(noteClass.this.getActivity(),R.layout.plants_item,listViewPlants);
        ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position,long id){
                Plant plant = listViewPlants.get(position);
                Intent intent = new Intent(noteClass.this.getActivity(),plants.class);
                intent.putExtra("name", listViewPlants.get(position).getName());
                startActivity(intent);
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initlist_view_plants(){
        for(Plant p:plantsList){
            listViewPlants.add(p);
        }
    }
}
