package com.example.tony.finalproject_plants;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 郭云浩 on 2017/11/24.
 */

public class NetUtils {
    static final int MSG_REFRESH = 0x222;

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
    public static void testDownload(final Context context, final Handler handler) {
        final String doc_path = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        final String pho_path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        final OkHttpClient client = new OkHttpClient();
        final String fileDir = doc_path + "/plants.xml";
        Random random = new Random();
        final String url_xml = NetUtils.URL + "/plants.xml"+"?v=" + random.nextInt();//http://ozyv1gfyf.bkt.clouddn.com/all.xml？v=123
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtils.download(url_xml, client, fileDir);
                File plantsfile = new File(doc_path+"/plants.xml");
                try {
                    Random random = new Random();
                    InputStream plantsfilestream = new FileInputStream(plantsfile);
                    List<Plant> plantList = XmlHelper.getPalntList(plantsfilestream);
                    for (Plant p : plantList){//查找是否存在
                        if (Plant.getPlantByName(p.getName()) != null){
                            continue;
                        }
                        String descriName = p.getDescriptionPath().substring(p.getDescriptionPath().lastIndexOf("/")+1);
                        String photoName = p.getphotoPath().substring(p.getphotoPath().lastIndexOf("/")+1);
                        Boolean download_descri = NetUtils.download(p.getDescriptionPath()+"?v="+random.nextInt(), client, doc_path+"/"+descriName);
                        Boolean download_photo = NetUtils.download(p.getphotoPath()+"?v="+random.nextInt(), client, pho_path+"/"+photoName);
                        if (download_descri && download_photo){
                            p.setDescriptionPath(descriName);
                            p.setphotoPath(photoName);
                            p.save();
                        }
                    }
                    handler.sendEmptyMessageAtTime(NetUtils.MSG_REFRESH, 0);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
