package com.habitissimo.appchallenge;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

public class Category {

    private String id;

    private String name;

    @SerializedName("normalized_name")
    private String res_name;

    private int image;

    public Category(String name, Context context){
        this.name = name;
        this.image = text2Image(name, context);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getResName() {
        return res_name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private int text2Image(String text, Context context) {
        int image = 0;
        if (text.equals(context.getString(R.string.cat_construccion))) {
            image = R.drawable.cat_construccion;
        } else if (text.equals(context.getString(R.string.cat_reformas))) {
            image = R.drawable.cat_reformas;
        } else if (text.equals(context.getString(R.string.cat_mudanzas))) {
            image = R.drawable.cat_mudanzas;
        } else if (text.equals(context.getString(R.string.cat_tecnicos))) {
            image = R.drawable.cat_tecnicos;
        } else if (text.equals(context.getString(R.string.cat_obras_menores))) {
            image = R.drawable.cat_obras_menores;
        } else if (text.equals(context.getString(R.string.cat_instaladores))) {
            image = R.drawable.cat_instaladores;
        } else if (text.equals(context.getString(R.string.cat_mantenimiento))) {
            image = R.drawable.cat_mantenimiento;
        } else if (text.equals(context.getString(R.string.cat_tiendas))) {
            image = R.drawable.cat_tiendas;
        }
        return image;
    }

}
