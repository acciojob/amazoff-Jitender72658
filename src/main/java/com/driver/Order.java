package com.driver;

import org.springframework.stereotype.Component;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(){

    }

    public Order(String id, String deliveryTime) {
          this.id = id;
        this.deliveryTime = Integer.valueOf(deliveryTime.substring(0,2))*60+Integer.valueOf(deliveryTime.substring(3,deliveryTime.length()));
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
