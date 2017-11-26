package com.example.tony.finalproject_plants;

import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapFragment;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tabNote;
    private TextView tabMap;
    private TextView tabAdd;
    private TextView tabFind;

    private noteClass noteFragment;
    private mapClass mapFragmet;
    private findClass findFragmet;
    private addClass addFragmet;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);

        setDefaultFragment();
        bindView();
        createSQL();
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        selected();
        switch(view.getId()){
            case R.id.txt_note:
                tabNote.setSelected(true);
                if (noteFragment==null){
                    noteFragment = new noteClass();
                }
                transaction.replace(R.id.fragment_container,noteFragment);
                break;
            case R.id.txt_map:
                tabMap.setSelected(true);
                if (mapFragmet==null){
                    mapFragmet = new mapClass();
                }
                transaction.replace(R.id.fragment_container,mapFragmet);
                break;
            case R.id.txt_find:
                tabFind.setSelected(true);
                if (findFragmet==null){
                    findFragmet = new findClass();
                }
                transaction.replace(R.id.fragment_container,findFragmet);
                break;
            case R.id.txt_more:
                tabAdd.setSelected(true);
                if (addFragmet==null){
                    addFragmet = new addClass();
                }
                transaction.replace(R.id.fragment_container,addFragmet);
                break;
        }
        transaction.commit();
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

    //设置初始默认fragment
    private void setDefaultFragment(){
        mapFragmet = new mapClass();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,mapFragmet);
        transaction.show(mapFragmet);
        transaction.commit();
    }

    //UI组件初始化与事件绑定
    private void bindView() {
        tabNote = (TextView)this.findViewById(R.id.txt_note);
        tabMap = (TextView)this.findViewById(R.id.txt_map);
        tabFind = (TextView)this.findViewById(R.id.txt_find);
        tabAdd = (TextView)this.findViewById(R.id.txt_add);

        tabNote.setOnClickListener(this);
        tabAdd.setOnClickListener(this);
        tabFind.setOnClickListener(this);
        tabMap.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void selected(){
        tabNote.setSelected(false);
        tabAdd.setSelected(false);
        tabMap.setSelected(false);
        tabFind.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction transaction){
        if(noteFragment!=null){
            transaction.hide(noteFragment);
        }
        if(mapFragmet!=null) {
            transaction.hide(mapFragmet);
        }
        if(findFragmet!=null){
            transaction.hide(findFragmet);
        }
        if(addFragmet!=null){
            transaction.hide(addFragmet);
        }
    }
}
