package com.hiskytech.feastfleet;

public class ModelImage {
    private String imageUrl;
    private String name;
    private String description;
    private String price;

    // Default constructor
    public ModelImage() {
        this.imageUrl = "";
        this.name = "";
        this.description = "";
        this.price = "";
    }

    // Parameterized constructor
    public ModelImage(String imageUrl, String name, String description, String price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getter and setter methods for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Getter and setter methods for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // Getter and setter methods for name
    public String getPrice() {
        return price;
    }

    public void setPrice(String name) {
        this.price = name;
    }

    // Getter and setter methods for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
