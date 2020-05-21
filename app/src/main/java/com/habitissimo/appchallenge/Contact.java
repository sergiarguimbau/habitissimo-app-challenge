package com.habitissimo.appchallenge;

import android.content.Context;

public class Contact {

    //Fields
    String name;
    String phone;
    String email;
    String location;

    //Constructors
    public Contact(String name, String phone, String email, String location)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
    }

    public String printContact(Context context){
        return context.getString(R.string.contact_copy, name, phone, email, location);
    }
}
