package com.ctis8.atoi.touchlock;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Atoi on 2.05.2017.
 */
public class Property implements Serializable{
    private String id, title, description, price,
        address, fromDate, toDate, url;

    public Property(String id, String title, String description, String price, String address, String fromDate, String toDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.address = address;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getUrl() {
        return url;
    }

    public String getToDate() {
        return toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }
}
