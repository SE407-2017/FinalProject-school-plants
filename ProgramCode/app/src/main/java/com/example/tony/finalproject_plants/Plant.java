package com.example.tony.finalproject_plants;

import android.widget.ImageButton;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 郭云浩 on 2017/10/22.
 */

public class Plant extends DataSupport implements Serializable {
    private int id;
    private String name;
    private String photoPath;
    private String descriptionPath;
    private double lat;
    private double lng;
    public Plant() {
    }

    public Plant(String name, String photoPath, String descriptionPath, double lat, double lng) {
        this.name = name;
        this.photoPath = photoPath;
        this.descriptionPath = descriptionPath;
        this.lat = lat;
        this.lng = lng;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getphotoPath() {
        return photoPath;
    }

    public void setphotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getDescriptionPath() {
        return descriptionPath;
    }

    public void setDescriptionPath(String descriptionPath) {
        this.descriptionPath = descriptionPath;
    }
    //所有plant
    public static List<Plant> getAllPlants(){
        List<Plant> plantList = DataSupport.findAll(Plant.class);
        return plantList;
    }
    //通过名字查找
    public static Plant getPlantByName(String name){
        Plant plant = DataSupport.where("name = ?", name).findFirst(Plant.class);
        return plant;
    }

}
