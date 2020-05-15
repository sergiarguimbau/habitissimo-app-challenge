package com.habitissimo.appchallenge;

public class Quotation {

    //Fields
    int category; // ImageView
    String subcategory;
    String description;
    Contact contact;

    //Constructors
    public Quotation(int category, String subcategory, String description, Contact contact)
    {
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.contact = contact;
    }
}
