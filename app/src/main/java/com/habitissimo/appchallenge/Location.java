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
}
