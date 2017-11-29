package com.example.tony.finalproject_plants;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class noteclass extends Fragment {
    @RequiresApi (api = Build.VERSION_CODES.KITKAT)
    public Bitmap getPhoto(String name){
        Plant plant = Plant.getPlantByName(name);
        FileUtils fu = new FileUtils(noteclass.this.getActivity().getApplicationContext());
        Bitmap img = fu.getPlantBitmap(plant);
        return img;
    }

    private class PlantAdapter extends ArrayAdapter<Plant> {
        private int resourceId;

        public PlantAdapter(Context context, int textViewResourceId, List<Plant> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Plant listPlants = getItem(position);
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.plantImage = view.findViewById(R.id.plant_image);
                viewHolder.plantName = view.findViewById(R.id.plant_name);
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

    private PlantAdapter adapter;
    private ListView listView;

    @Nullable
    @Override
    @RequiresApi (api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note, container, false);
        initlist_view_plants();

        adapter = new PlantAdapter(noteclass.this.getActivity(),R.layout.plants_item,listViewPlants);
        listView = view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position,long id){
                Plant plant = listViewPlants.get(position);
                Intent intent = new Intent(noteclass.this.getActivity(),plants.class);
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
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case NetUtils.MSG_REFRESH:
                    Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_SHORT).show();
                    listViewPlants = Plant.getAllPlants();
                    adapter = new PlantAdapter(noteclass.this.getActivity(),R.layout.plants_item,listViewPlants);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button download = getActivity().findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetUtils.testDownload(getActivity(), mHandler);
            }
        });
    }

}
