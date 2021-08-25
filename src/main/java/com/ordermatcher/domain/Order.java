package com.ordermatcher.domain;

import java.sql.Timestamp;

public class Order {

    private String type;
    private int volume;
    private int price;
    private Timestamp time;

    public Order(String type, int volume, int price,Timestamp time) {
        this.type = type;
        this.volume = volume;
        this.price = price;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString(){
        return this.type + " " + this.volume + "@" + this.price  + ", " + this.getTime();
    }
}
