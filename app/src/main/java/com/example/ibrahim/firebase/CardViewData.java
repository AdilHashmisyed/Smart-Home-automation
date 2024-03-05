package com.example.ibrahim.firebase;

import android.widget.ImageView;

public class CardViewData {
    String name, ssid, status, image_name_v;
    int id;

    public CardViewData(String ssid, String status,int id) {
        this.ssid = ssid;
        this.status = status;
        this.image_name_v = "@drawable/abc";
        this.id=id;
    }

    public CardViewData(String ssid, String name, String status) {
        this.ssid = ssid;
        this.name = name;
        this.status = status;
        this.image_name_v = "@drawable/setting";
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSsid() {
        return ssid;
    }

    public String getStatus() {
        return status;
    }
    public int getId() {
        return id;
    }

    public String getimage_name() {
        return image_name_v;

        
    }



    public void setStatus(String status) {
        this.status = status;
    }
}