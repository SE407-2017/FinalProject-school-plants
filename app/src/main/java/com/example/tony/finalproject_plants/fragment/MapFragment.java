package com.example.tony.finalproject_plants.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.tony.finalproject_plants.R;
import com.baidu.mapapi.*;

import java.util.List;

/**
 * Created by SHIYONG on 2017/10/13.
 */

public class MapFragment extends Fragment {
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationManager locationManager;
    private MyLocationListener locationListener;
    private String provider;
    private boolean isFirstLocate=true;
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view=layoutInflater.inflate(R.layout.fragment_map,null);
        mapView=(MapView)view.findViewById(R.id.map_view);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        LatLng point = new LatLng(39.963175, 116.400244);
        BitmapDescriptor bitmapDescriptor= BitmapDescriptorFactory.fromResource(R.drawable.find);
        OverlayOptions options=new MarkerOptions()
                .position(point)
                .draggable(false)
                .icon(bitmapDescriptor);
        baiduMap.addOverlay(options);
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        locationManager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        //获取所有有用的位置提供器
        List<String> providerList=locationManager.getProviders(true);
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
            provider=LocationManager.GPS_PROVIDER;
        }else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
            provider=LocationManager.NETWORK_PROVIDER;
        }else{
            //没有可用的位置提供器
            Toast.makeText(getActivity(),"无法获取位置！",Toast.LENGTH_SHORT).show();
            return ;
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            Location location=locationManager.getLastKnownLocation(provider);
            if(location!=null){
                navigateTo(location);
            }
            locationListener=new MyLocationListener();
            locationManager.requestLocationUpdates(provider,5000,1,locationListener);
        }
    }
    private void navigateTo(Location location){
        if(isFirstLocate){
            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update=MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(update);
            update=MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate=false;
        }
        MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
        MyLocationData locationData=locationBuilder.latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        baiduMap.setMyLocationData(locationData);

    }
    private class MyLocationListener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            if(location!=null){
                isFirstLocate=true;
                navigateTo(location);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        if(locationListener!=null){
            locationManager.removeUpdates(locationListener);
        }
    }
}
