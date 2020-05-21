package com.habitissimo.appchallenge;

import android.content.Context;

public class Quotation {

    //Fields
    Category category;
    Category subcategory;
    String description;
    Contact contact;

    //Constructors
    public Quotation(Category category, Category subcategory, String description, Contact contact)
    {
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.contact = contact;
    }

    public String printQuotation(Context context){
        String result = context.getString(R.string.quotation).toUpperCase();
        result = result.concat("\n");
        result = result.concat(context.getString(R.string.category));
        result = result.concat(": ");
        result = result.concat(category.getName());
        result = result.concat("\n");
        result = result.concat(context.getString(R.string.subcategory));
        result = result.concat(": ");
        result = result.concat(subcategory.getName());
        result = result.concat("\n");
        result = result.concat(context.getString(R.string.description));
        result = result.concat(": ");
        result = result.concat(description);
        result = result.concat("\n");
        result = result.concat(contact.printContact(context));
        return result;
    }
}
