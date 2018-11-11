package com.example.francois.exam_android;

import java.text.DecimalFormat;

public class Destination {

String type;
String title;
String id;
String url;
double distance;
private static DecimalFormat df3 = new DecimalFormat(".###");

    public Destination(String type,String title,String id,String url,double distance){
        this.type = type;
        this.title = title;
        this.id = id;
        this.url = url;
        this.distance = distance;
    }

    public String arrondiDistance(){
        return String.format("%.2f", getDistance());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String URL) {
        this.url = URL;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", distance=" + distance +
                '}';
    }
}
