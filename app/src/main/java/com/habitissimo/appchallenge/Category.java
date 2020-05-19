package com.habitissimo.appchallenge;

import com.google.gson.annotations.SerializedName;

public class Category {

    private String id;

    @SerializedName("normalized_name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
