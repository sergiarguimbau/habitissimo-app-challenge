package com.habitissimo.appchallenge;

import com.google.gson.annotations.SerializedName;

public class Location {

    private int id;

    private String zip;

    private String name;

    @SerializedName("slug")
    private String res_name;

    @SerializedName("geo_lat")
    private double lat;

    @SerializedName("geo_lng")
    private double lng;

    public int getId() {
        return id;
    }

    public String getZip() {
        return zip;
    }

    public String getName() {
        return name;
    }

    public String getResName() {
        return res_name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResName(String res_name) {
        this.res_name = res_name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
