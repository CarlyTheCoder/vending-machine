package com.techelevator;

public class Item {

    private String code;
    private String name;
    private double price;
    private String type;
    private int count = 5;

    public Item(String code, String name, double price, String type) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", count=" + count +
                '}';
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count -= count;
    }
}


