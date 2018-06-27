package com.example.rog.register;

/**
 * Created by ROG on 4/4/2018.
 */

public class HistoryList {
    private int id;
    private String desc;
    private String location;
    private String type;
    private String date;
    private String image;

    public HistoryList(String desc, String location, String type, String date, String image) {
        this.desc = desc;
        this.location = location;
        this.type = type;
        this.date = date;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getLoc() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }
}
