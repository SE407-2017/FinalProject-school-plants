package com.example.tony.finalproject_plants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 郭云浩 on 2017/10/22.
 */

public class FileUtils {

    private Context context;

    public FileUtils(Context context) {
        this.context = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getPlantDescription(Plant plant){
        try(InputStream fin = context.getAssets().open(plant.getDescriptionPath())){
            String temp = "";
            String descri = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            while ((temp = br.readLine()) != null){
                descri += temp;
            }
            return descri;
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Bitmap getPlantBitmap(Plant plant){
        try(InputStream fin = context.getAssets().open(plant.getphotoPath())){
            Bitmap bitmap = BitmapFactory.decodeStream(fin);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
