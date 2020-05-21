package com.habitissimo.appchallenge;

import android.content.Context;

public class Contact {

    //Fields
    private String name;
    private String phone;
    private String email;
    private String location;

    //Constructors
    public Contact(String name, String phone, String email, String location)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String printContact(Context context){
        return context.getString(R.string.contact_copy, name, phone, email, location);
    }
}
