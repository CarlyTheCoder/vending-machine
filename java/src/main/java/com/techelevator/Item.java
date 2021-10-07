package com.techelevator;

public class Item {

    private String code;
    private String name;
    private double price;
    private String type;

    public Item(String code, String name, double price, String type) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
