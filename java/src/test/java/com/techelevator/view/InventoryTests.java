package com.techelevator.view;

import com.techelevator.Inventory;
import com.techelevator.Item;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class InventoryTests {

    @Test
    public void entry_outputs_correct_categories() {
        List<Item> items = new ArrayList<>();
        Inventory inventory = new Inventory(items);

        String expected = "A1";
        String actual = inventory.getItems().get(0).getCode();

        Assert.assertEquals(expected, actual);
    }

}
