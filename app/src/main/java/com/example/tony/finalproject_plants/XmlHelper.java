package com.example.tony.finalproject_plants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭云浩 on 2017/10/23.
 */

public class XmlHelper {
    public static List<Plant> getPalntList(InputStream xml) throws Exception{
        List<Plant> plantList = null;
        Plant plant = null;
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser pullParser = factory.newPullParser();
        pullParser.setInput(xml, "UTF-8");
        int eventType = pullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    plantList = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("plant".equals(pullParser.getName())){
                        plant = new Plant();
                        int id = Integer.parseInt(pullParser.getAttributeValue(0));
                        plant.setId(id);
                    }else if ("name".equals(pullParser.getName())){
                        String name = pullParser.nextText();
                        plant.setName(name);
                    }else if ("description_path".equals(pullParser.getName())){
                        String description_path = pullParser.nextText();
                        plant.setDescriptionPath(description_path);
                    }else if ("photo_path".equals(pullParser.getName())){
                        String photo_path = pullParser.nextText();
                        plant.setphotoPath(photo_path);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("plant".equals(pullParser.getName())){
                        plantList.add(plant);
                        plant = null;
                    }
                    break;
            }
            eventType = pullParser.next();
        }
        return plantList;
    }
}
