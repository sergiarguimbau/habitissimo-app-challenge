package com.habitissimo.appchallenge;

import android.content.Context;

public class Quotation {

    //Fields
    private Category category;
    private Category subcategory;
    private String description;
    private Contact contact;

    //Constructors
    public Quotation(Category category, Category subcategory, String description, Contact contact)
    {
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.contact = contact;
    }

    public Category getCategory() {
        return category;
    }

    public Category getSubcategory() {
        return subcategory;
    }

    public String getDescription() {
        return description;
    }

    public Contact getContact() {
        return contact;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSubcategory(Category subcategory) {
        this.subcategory = subcategory;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContact(Contact contact) {
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
