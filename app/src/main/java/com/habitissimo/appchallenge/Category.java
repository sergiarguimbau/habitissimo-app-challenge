package com.habitissimo.appchallenge;

import com.google.gson.annotations.SerializedName;

public class Category {

    private String id;

    private String name;

    @SerializedName("normalized_name")
    private String res_name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getResName() {
        return res_name;
    }
}
