package com.pluralsight;

public class Info {
    private String date;
    private String time;
    private String desc;
    private String vendor;
    private double price;

    public Info(String date, String time, String  desc, String vendor, double price) {
        this.date = date;
        this.time = time;
        this.desc = desc;
        this.vendor = vendor;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDesc() {
        return desc;
    }

    public String getVendor() {
        return vendor;
    }

    public double getPrice() {
        return price;
    }
}
