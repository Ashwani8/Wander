package com.example.admin.wander;

public class Attraction {
    // step 1 create variables
    private int image; // because i am going to create a static recycler, i.e.
    // we will use images from drawable and not from network
    private String title, shortDescription;
    // step 2 define the variable and then create a constructor for all just by
    // right clicking >generate>constructor select all the value
    public Attraction(int image, String title, String shortDescription) {
        this.image = image;
        this.title = title;
        this.shortDescription = shortDescription;
    }
    // step 3 similarly we can create all the getters by right clicking>generate>getters

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }
    // step 4 create an adapter class for recycler view
}
