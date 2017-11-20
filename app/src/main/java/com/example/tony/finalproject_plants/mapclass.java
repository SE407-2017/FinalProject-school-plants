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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by zhang on 2017/10/22.
 */

public class mapclass extends android.app.Fragment {

    // 百度地图相关
    private TextureMapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private Marker mMarkerA;
    private MapStatusUpdate msu = null;

    // 定位相关
    private boolean isFirstGetLocation=true;
    private LocationClient mLocationClient = null;
    MyLocationListener mLocationListener;

    //构建MarkerOption，用于在地图上添加Marker
    LatLng pointA = new LatLng(31.0290702492,121.4414345747);
    BitmapDescriptor bdA = BitmapDescriptorFactory
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
// 设置地图初始化缩放比例
        msu = MapStatusUpdateFactory.zoomTo(17.0f);// 这里的显示等级第15级
        mBaiduMap.setMapStatus(msu);
        mLocationClient = new LocationClient(getActivity().getApplicationContext()); //定位客户端
        mLocationListener = new MyLocationListener();  //定位监听器
//绑定定位监听器
        mLocationClient.registerLocationListener(mLocationListener);

//对定位参数进行配置 LocationClientOption
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);

//设置定位参数
        mLocationClient.setLocOption(option);
        mBaiduMap.setMyLocationEnabled(true);//开启定位图层
        initOverLay();
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()
        {
            public boolean onMarkerClick(final Marker marker)
            {
                //创建InfoWindow展示的view
                Button button = new Button(getActivity().getApplicationContext());
                button.setBackgroundResource(R.drawable.popup);

//定义用于显示该InfoWindow的坐标点
                if(marker == mMarkerA)
                {
                    button.setText("石楠花");
                    InfoWindow mInfoWindow = new InfoWindow(button, pointA, -47);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                    button.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mapclass.this.getActivity(),plants.class);
                            intent.putExtra("name", "photinia");
                            intent.setFlags(6);
                            startActivity(intent);
                        }
                    });
                }
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
        bdA.recycle();

    }

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

    public void initOverLay()
    {

        //构建Marker图标

        OverlayOptions optionA = new MarkerOptions()
                .position(pointA)
                .icon(bdA);

        //在地图上添加Marker，并显示
        mMarkerA = (Marker)mBaiduMap.addOverlay(optionA);

    };


}


