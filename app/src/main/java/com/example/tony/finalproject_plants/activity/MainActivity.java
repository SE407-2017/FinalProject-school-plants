package com.example.tony.finalproject_plants.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.example.tony.finalproject_plants.R;
import com.example.tony.finalproject_plants.fragment.*;

/**
 * Created by SHIYONG on 2017/10/13.
 * This is the main activity.
 */

public class MainActivity extends Activity {
    private TabHost mTabHost;//选项卡
    private String[] titles=new String[]{"汇总","附近","发现","搜索"};//各选项名称
    private String[] tags=new String[]{"Total","Map","Find","Search"};//各选项标记
    private int[] icons=new int[]{R.drawable.total,R.drawable.map,R.drawable.find,R.drawable.search};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initTab();
    }

    private void initTab(){
        mTabHost=(TabHost)findViewById(R.id.mTabHost);
        mTabHost.setup();
        for(int i=0;i<titles.length;i++){
            TabHost.TabSpec tabSpec=mTabHost.newTabSpec(tags[i]);
            View view=getLayoutInflater().inflate(R.layout.tab,null);
            TextView tabTitle=(TextView)view.findViewById(R.id.tabTitle);
            ImageView tabicon=(ImageView)view.findViewById(R.id.tabIcon);
            tabTitle.setText(titles[i]);
            tabicon.setImageResource(icons[i]);
            tabSpec.setIndicator(view);
            tabSpec.setContent(R.id.realcontent);
            mTabHost.addTab(tabSpec);
        }
        mTabHost.setOnTabChangedListener(new MyTabChangedListener());
        mTabHost.setCurrentTabByTag("Total");
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.realcontent,new ListFragment(),"Total");
        fragmentTransaction.commit();
    }
    class MyTabChangedListener implements TabHost.OnTabChangeListener{
        public void onTabChanged(String tag){
            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
            if(tag.equalsIgnoreCase("Total")){
                fragmentTransaction.replace(R.id.realcontent,new ListFragment(),"Total");
            }else if(tag.equalsIgnoreCase("Map")){
                fragmentTransaction.replace(R.id.realcontent,new MapFragment(),"Map");
            }else if(tag.equalsIgnoreCase("Find")){
                fragmentTransaction.replace(R.id.realcontent,new FindFragment(),"Find");
            }else if(tag.equalsIgnoreCase("Search")){
                fragmentTransaction.replace(R.id.realcontent,new SearchFragment(),"Search");
            }
            fragmentTransaction.commit();
        }
    }
}
