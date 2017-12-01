package com.example.tony.finalproject_plants;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 郭云浩 on 2017/10/23.
 */

public class SharedHelper {
    private Context context;

    public SharedHelper(Context context) {
        this.context = context;
    }
    public void saveStartStatus(Boolean first_start){
        SharedPreferences sharedPreferences = context.getSharedPreferences("start_status", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_first_start", first_start);
        editor.commit();
    }
    public Boolean readStartStatus(){
        SharedPreferences preferences = context.getSharedPreferences("start_status", Context.MODE_PRIVATE);
        return preferences.getBoolean("is_first_start", true);
    }
}
