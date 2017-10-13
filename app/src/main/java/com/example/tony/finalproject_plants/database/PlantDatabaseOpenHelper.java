package com.example.tony.finalproject_plants.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tony.finalproject_plants.model.Plant;

/**
 * Created by SHIYONG on 2017/10/13.
 */

public class PlantDatabaseOpenHelper extends SQLiteOpenHelper {
    public PlantDatabaseOpenHelper(Context context, String name,
                                   SQLiteDatabase.CursorFactory cursorFactory,int version){
        super(context,name,cursorFactory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase database){

    }

    @Override
    public void onUpgrade(SQLiteDatabase database,int version,int flag){

    }
}
