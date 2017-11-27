package com.example.tony.finalproject_plants;


import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;


import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapFragment;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tabnote;
    private TextView tabmap;
    private TextView tabmore;
    private TextView tabfind;

    private FrameLayout ly_content;

    private noteclass f1;
    private mapclass f2;
    private findclass f3;
    private moreclass f4;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mapclass f = new mapclass();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, f);
        transaction.show(f);
        transaction.commit();

        bindView();

        createSQL();
        //testDownload();
    }

    private void testDownload() {
        final String doc_path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        final String pho_path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        final OkHttpClient client = new OkHttpClient();
        final String fileDir = doc_path + "/plants.xml";
        final String url_xml = NetUtils.URL + "/all.xml";//http://ozyv1gfyf.bkt.clouddn.com/all.xml
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtils.download(url_xml, client, fileDir);
                File plantsfile = new File(doc_path+"/plants.xml");
                try {
                    InputStream plantsfilestream = new FileInputStream(plantsfile);
                    List<Plant> plantList = XmlHelper.getPalntList(plantsfilestream);
                    for (Plant p : plantList){//查找是否存在
                        if (Plant.getPlantByName(p.getName()) != null){
                            continue;
                        }
                        String descriName = p.getDescriptionPath().substring(p.getDescriptionPath().lastIndexOf("/")+1);
                        String photoName = p.getphotoPath().substring(p.getphotoPath().lastIndexOf("/")+1);
                        Boolean download_descri = NetUtils.download(p.getDescriptionPath(), client, doc_path+"/"+descriName);
                        Boolean download_photo = NetUtils.download(p.getphotoPath(), client, pho_path+"/"+photoName);
                        if (download_descri && download_photo){
                            p.setDescriptionPath(descriName);
                            p.setphotoPath(photoName);
                            p.save();
                        }
                   }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //数据库
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    //创建数据库
    private void createSQL(){
        SQLiteDatabase db = Connector.getDatabase();
        try {
            DataSupport.deleteAll(Plant.class);
            InputStream xmlFile = MainActivity.this.getAssets().open("plants.xml");
            List<Plant> plantList = XmlHelper.getPalntList(xmlFile);
            DataSupport.saveAll(plantList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void testDatabase(){
        //找出所有植物
        List<Plant> plantList = Plant.getAllPlants();
        Log.d("mainactivity", "testDatabase: " + plantList.size());
        for (Plant p : plantList){
            Log.d("mainactivity", "testDatabase: " + p.getDescriptionPath());
        }
        //根据名称查找数据
        Plant plant = Plant.getPlantByName("example plant1");
        //通过文件处理工具加载对应的描述文字及图片
        FileUtils fu = new FileUtils(getApplicationContext());
        String des = fu.getPlantDescription(plant);//描述
        Bitmap img = fu.getPlantBitmap(plant);//图片
        Log.d("mainactivity", "testDatabase: " + des);
    }
    /*获得植物名称、图片、描述
     *描述文件文字存在main/assets/plant_descriptions
     * 图片存在main/assets/plant_photos
     * *assets/plants.xml文件记录名称、图片、描述文字的对应关系，添加图片文字需要在plant.xml文件了添加内容
     *1.获得Plant对象:Plant.getAllPalnts() 或者 Plant.getPlantByName(String name)
     *2. FileUtils fileUtils = new FileUtils(getApplicationContext());
     *3.String description = fileUtils.getPlantDescription(plant);
     *4.Bitmap img = fileUtils.getPlantBitmap(plant);
    */
    //数据库结束

    //UI组件初始化与事件绑定
    private void bindView() {
        tabnote = (TextView)this.findViewById(R.id.txt_note);
        tabmap = (TextView)this.findViewById(R.id.txt_map);
        tabfind = (TextView)this.findViewById(R.id.txt_find);
        tabmore = (TextView)this.findViewById(R.id.txt_more);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

        tabnote.setOnClickListener(this);
        tabmore.setOnClickListener(this);
        tabfind.setOnClickListener(this);
        tabmap.setOnClickListener(this);

    }

    //重置所有文本的选中状态
    public void selected(){
        tabnote.setSelected(false);
        tabmore.setSelected(false);
        tabmap.setSelected(false);
        tabfind.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null) {
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
        if(f4!=null){
            transaction.hide(f4);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_note:
                selected();
                tabnote.setSelected(true);
                    f1 =  new noteclass();
                    transaction.add(R.id.fragment_container,f1);
                break;

            case R.id.txt_map:
                selected();
                tabmap.setSelected(true);
                    f2 = new mapclass();
                    transaction.add(R.id.fragment_container,f2);
                break;

            case R.id.txt_find:
                selected();
                tabfind.setSelected(true);
                    f3 = new findclass();
                    transaction.add(R.id.fragment_container,f3);
                break;

            case R.id.txt_more:
                selected();
                tabmore.setSelected(true);
                    f4 = new moreclass();
                    transaction.add(R.id.fragment_container,f4);
                break;
        }

        transaction.commit();
    }
}
