package com.example.tony.finalproject_plants;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.baidu.mapapi.http.HttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 郭云浩 on 2017/11/24.
 */

public class NetUtils {
    static final String URL="http://ozyv1gfyf.bkt.clouddn.com";//http://ozyv1gfyf.bkt.clouddn.com/plants.xml
    public static boolean download(String url, OkHttpClient client, String filepath){
        try{
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                InputStream in = response.body().byteStream();
                byte[] bytes = new byte[1024];
                int len = 0;
                File file = new File(filepath);
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                while ((len = in.read(bytes)) > 0){
                    outputStream.write(bytes, 0, len);
                }
                in.close();
                outputStream.close();
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
//
//    public static boolean ckeck_or_create_file_dir(Context context ){
//        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"photos");
//        File file1 = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "description");
//        if (file.mkdir() && file1.mkdir()){
//            return true;
//        }
//        return false;
//    }
}
