package com.ctis8.atoi.touchlock;

/**
 * Created by Atoi on 11.04.2017.
 */
public class Advertisement {
    private String city, checkInDate, checkOutDate, type
            , description;

    private int numGuest;
    private boolean isPetAllowed;


    public Advertisement(String city, boolean isPetAllowed, String type, int numGuuest, String description, String checkOutDate, String checkInDate) {
        this.city = city;
        this.isPetAllowed = isPetAllowed;
        this.type = type;
        this.numGuest = numGuuest;
        this.description = description;
        this.checkOutDate = checkOutDate;
        this.checkInDate = checkInDate;
    }

    public String getCity() {
        return city;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getNumGuest() {
        return numGuest;
    }

    public boolean isPetAllowed() {
        return isPetAllowed;
    }
}
