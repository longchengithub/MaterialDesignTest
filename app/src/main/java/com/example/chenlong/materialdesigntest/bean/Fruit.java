package com.example.chenlong.materialdesigntest.bean;

/**
 * Created by ChenLong on 2017/1/27.
 */

public class Fruit
{
    private String name;
    private int imageId;

    public String getName()
    {
        return name;
    }

    public Fruit(String name, int imageId)
    {
        this.name = name;
        this.imageId = imageId;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getImageId()
    {
        return imageId;
    }

    public void setImageId(int imageId)
    {
        this.imageId = imageId;
    }
}
