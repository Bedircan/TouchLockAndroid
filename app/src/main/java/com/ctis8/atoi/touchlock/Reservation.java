package com.ctis8.atoi.touchlock;

/**
 * Created by Atoi on 2.05.2017.
 */
public class Reservation {
    String uniqueId, resId, from, to;

    public Reservation(String uniqueId, String resId, String from, String to)
    {
        this.uniqueId = uniqueId;
        this.resId = resId;
        this.from = from;
        this.to = to;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getResId() {
        return resId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
