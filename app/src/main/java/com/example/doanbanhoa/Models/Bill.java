package com.example.doanbanhoa.Models;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private String customerId, day, time, Address;
    private List<Item> items;
    private int totalPrice =0;

    public Bill() {
        this.customerId = "";
        this.day = "";
        this.time = "";
        Address = "223";
    }

    public Bill(String customerId, String day, String time, String address, List<Item> items) {
        this.customerId = customerId;
        this.day = day;
        this.time = time;
        Address = address;
        this.items = items;
        total(items);// unsure
    }



    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private void total(List<Item> items1){
        for(Item i : items1){
            this.totalPrice += i.getTongGia();
        }
    }

}
