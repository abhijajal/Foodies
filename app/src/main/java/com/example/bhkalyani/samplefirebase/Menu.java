package com.example.bhkalyani.samplefirebase;

/**
 * Created by Bhkalyani on 3/28/2017.
 */

public class Menu
{
    private String Image,Name;
    private Long Price;

    public Menu(){

    }

    public Menu(String image, String name, Long price) {
        Image = image;
        Name = name;
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }
}
