package com.example.tony.finalproject_plants.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tony.finalproject_plants.R;
import com.example.tony.finalproject_plants.model.Plant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHIYONG on 2017/10/13.
 *
 */

public class PlantDatabase{
    private static String DATABASE_NAME="plants";
    private static final int VERSION=1;
    private static PlantDatabase plantDatabase;
    private SQLiteDatabase myDatabase;
    private PlantDatabase(Context context){
        PlantDatabaseOpenHelper helper=new PlantDatabaseOpenHelper(context,DATABASE_NAME,null,VERSION);
        myDatabase=helper.getWritableDatabase();
    }
    public synchronized static PlantDatabase getPlantDatabase(Context context){
        if(plantDatabase==null){
            plantDatabase=new PlantDatabase(context);
            plantDatabase.initPlants();
        }
        return plantDatabase;
    }
    public void initPlants(){
        //myDatabase.delete("Plants",null,null);
        /*
        ContentValues values=new ContentValues();
        values.put("plant_name","石楠");
        values.put("short_info","蔷薇目、蔷薇科、石楠属木本植物，常绿乔木类");
        values.put("image_id", R.drawable.shinan);
        myDatabase.insert("Plants",null,values);
        values.clear();
        values.put("plant_name","桃花");
        values.put("short_info","桃花是中国传统的园林花木，其树态优美，枝干扶疏，花朵丰腴，色彩艳丽，为早春重要观花树种之一");
        values.put("image_id", R.drawable.taohua);
        myDatabase.insert("Plants",null,values);
        values.clear();
        values.put("plant_name","梨花");
        values.put("short_info","蔷薇科梨属，梨树的花朵,春季开花，花色洁白，如同雪花，具有浓烈香味");
        values.put("image_id", R.drawable.lihua);
        myDatabase.insert("Plants",null,values);*/
    }
    public List<Plant> loadPlants(){
        List<Plant> plants=new ArrayList<Plant>();
        Cursor cursor=myDatabase.query("Plants",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Plant plant=new Plant();
                plant.setPlant_id(cursor.getInt(cursor.getColumnIndex("plant_id")));
                plant.setPlant_name(cursor.getString(cursor.getColumnIndex("plant_name")));
                plant.setShort_info(cursor.getString(cursor.getColumnIndex("short_info")));
                plant.setImage_id(cursor.getInt(cursor.getColumnIndex("image_id")));
                plants.add(plant);
            }while (cursor.moveToNext());
        }
        if(cursor!=null) cursor.close();
        return plants;
    }

    public Plant queryPlant(String plant_name){
        Plant plant=null;
        Cursor cursor=this.myDatabase.query("Plants",new String[]{"plant_name"},
                "plant_name=?",new String[]{plant_name},null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            plant.setImage_id(cursor.getInt(cursor.getColumnIndex("plant_id")));
            plant.setImage_id(cursor.getInt(cursor.getColumnIndex("image_id")));
            plant.setShort_info(cursor.getString(cursor.getColumnIndex("short_info")));
            plant.setPlant_name(plant_name);
        }
        return plant;
    }
}
