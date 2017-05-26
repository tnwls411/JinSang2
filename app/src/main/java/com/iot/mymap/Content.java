package com.iot.mymap;

import java.io.Serializable;

public class Content implements Serializable {

    int no;
    String place;
    int count;
    int distance;
    String Latitude;
    String Longitude;

    public Content(int no, String place, int count, int distance, String Latitude, String Longitude) {
        this.no = no;
        this.place = place;
        this.count = count;
        this.distance = distance;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public Content(String latitude, String longitude){

    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
