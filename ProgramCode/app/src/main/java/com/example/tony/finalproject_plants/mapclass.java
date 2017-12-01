package com.example.tony.finalproject_plants;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.baidu.location.h.k.v;
import static com.example.tony.finalproject_plants.Plant.getAllPlants;

/**
 * Created by zhangzhenrui on 2017/10/22.
 */

public class mapclass extends android.app.Fragment {

    //百度地图相关
    private TextureMapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private MapStatusUpdate msu = null;

    //定位相关
    private boolean isFirstGetLocation=true;
    private LocationClient mLocationClient = null;
    MyLocationListener mLocationListener;

    //Marker相关
    private List<LatLng> latLng = new ArrayList<>();
    private List<Marker> markerList = new ArrayList<>();
    private List<OverlayOptions> overlayOptions = new ArrayList<>();
    private List<Plant> plantList = getAllPlants();
    BitmapDescriptor bd = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_gcoding);

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.map, container, false);
        mMapView = (TextureMapView) view.findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();// 从地图视图中获取百度地图实例对象

        msu = MapStatusUpdateFactory.zoomTo(17.0f);// 设置地图初始化缩放比例
        mBaiduMap.setMapStatus(msu);
        mLocationClient = new LocationClient(getActivity().getApplicationContext()); //定位客户端
        mLocationListener = new MyLocationListener();  //定位监听器
        mLocationClient.registerLocationListener(mLocationListener);//绑定定位监听器

        //对定位参数进行配置
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);

        mLocationClient.setLocOption(option);//设置定位参数
        mBaiduMap.setMyLocationEnabled(true);//开启定位图层

        //初始化标记并显示
        addPlantsOverlay();

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()
        {
            public boolean onMarkerClick(final Marker marker)
            {
                //点击出现简介按钮
                Button button = new Button(getActivity().getApplicationContext());
                button.setBackgroundResource(R.drawable.popup);

                final Plant plant = (Plant)marker.getExtraInfo().get("plant");

                button.setText(plant.getName());
                LatLng latLng = new LatLng(plant.getLat(), plant.getLng());
                InfoWindow mInfoWindow = new InfoWindow(button, latLng, -47);
                mBaiduMap.showInfoWindow(mInfoWindow);

                //定义用于显示该InfoWindow的坐标点
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mapclass.this.getActivity(),results.class);
                        intent.putExtra("name", plant.getName());
                        startActivity(intent);
                    }
                });

                return false;
            }
        });
        return view;

    }

    @Override
    public void onStart()
    {
        super.onStart();
        if (mLocationClient.isStarted()==false)
        {
            mLocationClient.start();//开启定位
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mMapView != null)
        {
            mMapView.onResume(); // 使百度地图地图控件和Fragment的生命周期保持一致
        }

    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mMapView != null)
        {
            mMapView.onPause(); // 使百度地图地图控件和Fragment的生命周期保持一致
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();//停止定位
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mMapView != null)
        {
            mMapView.onDestroy(); // 使百度地图地图控件和Fragment的生命周期保持一致
        }
        bd.recycle();

    }

    //定位监听器
    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation location)
        {

            //判断是否为首次获取到位置数据
            if (isFirstGetLocation)
            {
                //如果为首次定位，则直接定位到当前用户坐标
                LatLng latLng=new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate msuLocationMapStatusUpdate=MapStatusUpdateFactory//
                        .newLatLng(latLng);
                mBaiduMap.animateMapStatus(msuLocationMapStatusUpdate);

                isFirstGetLocation=false;
            }
        }
    }

    public void addPlantsOverlay()
    {
        int len = plantList.size();
        for (int i=0; i<len; i++)
        {
            Plant plant  = plantList.get(i);
            latLng.add(new  LatLng(plant.getLat(), plant.getLng()));
            overlayOptions.add(new MarkerOptions().position(latLng.get(i))
                    .icon(bd));
            Marker marker = (Marker) (mBaiduMap.addOverlay(overlayOptions.get(i)));
            Bundle bundle = new Bundle();
            bundle.putSerializable("plant", plant);
            marker.setExtraInfo(bundle);
        }
    }

}