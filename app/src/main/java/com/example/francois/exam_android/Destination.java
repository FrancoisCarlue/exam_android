package com.example.francois.exam_android;

public class Destination {

String type;
String title;
String id;
String URL;
int distance;

    public Destination(String type,String title,String id,String URL){
        this.type = type;
        this.title = title;
        this.id = id;
        this.URL = URL;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
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
