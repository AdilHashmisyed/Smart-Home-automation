package com.example.ibrahim.firebase;

public class control_switch {
    private String name;
    private int imageResource,color;
    private String serialnumber;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getserialnumber() {
        return serialnumber;
    }

    public int getcolor(){
        return color;
    }

    public void setserialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public control_switch(String name, int imageResource, String serialnumber,int color) {
        this.name = name;
        this.imageResource = imageResource;
        this.serialnumber = serialnumber;
        this.color=color;
    }
}
