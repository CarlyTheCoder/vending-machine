package com.techelevator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    private List<Item> items = new ArrayList<>();

    public Inventory(List<Item> items) {
        String path = "vendingmachine.csv";
        Scanner fileScanner = new Scanner(path);
        File inputFile = new File(path);

        while(fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] categories = line.split("\\|");
            Item item = new Item(categories[0], categories[1], Double.parseDouble(categories[2]), categories[3]);
            items.add(item);
        }
    }

    public List<Item> getItems() {
        return items;
    }
}
