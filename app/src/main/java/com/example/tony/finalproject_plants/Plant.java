package com.example.tony.finalproject_plants;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 郭云浩 on 2017/10/22.
 */

public class Plant extends DataSupport {
    private int id;
    private String name;
    private String photoPath;
    private String descriptionPath;

    public Plant() {
    }

    public Plant(int id, String name, String photoPath, String descriptionPath) {
        this.id = id;
        this.name = name;
        this.photoPath = photoPath;
        this.descriptionPath = descriptionPath;
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
    public static List<Plant> getAllPalnts(){
        List<Plant> plantList = DataSupport.findAll(Plant.class);
        return plantList;
    }
    //通过名字查找
    public static Plant getPlantByName(String name){
        Plant plant = DataSupport.where("name = ?", name).findFirst(Plant.class);
        return plant;
    }

}
