package com.techelevator.view;

import com.techelevator.Item;
import com.techelevator.VendingMachineCLI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;


public class VendingMachine {

    String vendingActivity;

    public static void getTransactionDateTimeStamp(String vendingActivity) {
        LocalDate date = LocalDate.now();
        Calendar calendar = Calendar.getInstance();
        String dataFile = "Log.txt";
        try (PrintWriter dataOutput = new PrintWriter(new FileOutputStream(dataFile, true))) {
            dataOutput.println(date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + date.getYear() + " " + calendar.get(calendar.HOUR_OF_DAY)
                    + ":" + calendar.get(calendar.MINUTE) + ":" + calendar.get(calendar.SECOND) + " " + "UTC" + " " + vendingActivity);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }

    public static BigDecimal feedMoney(BigDecimal balance, Scanner userInput) {
        boolean moreMoney = true;
        //BigDecimal totalDollars = balance;
        while (moreMoney) {
            System.out.println("Please enter money in whole dollar amounts");
            BigDecimal dollars = new BigDecimal(userInput.nextInt());
            userInput.nextLine();

            balance = balance.add(dollars);
            System.out.println("current money provided: " + balance);
            VendingMachine.getTransactionDateTimeStamp("Feed Money: $" + dollars + " " + "$" +balance);
            boolean isValid = false;
            while (isValid == false) {
                System.out.println("Do you have more bills?");
                String answer = userInput.nextLine().toLowerCase();
                if (answer.equals("n")) {
                    moreMoney = false;
                    isValid = true;
                } else if (answer.equals("y")) {
                    moreMoney = true;
                    isValid = true;
                } else {
                    System.out.println("invalid input, try again");
                }
            }
        }
        return balance;
    }

    public static BigDecimal makeSelection(List<Item> currentStock, Scanner userInput, BigDecimal shoppingCart,
                                     List<Item> itemList, BigDecimal balance) {
        VendingMachineCLI.displayVendingMachineItems(currentStock);
        userInput.nextLine();
        System.out.println("Please enter code of item: ");
        String codeSelection = userInput.nextLine().toUpperCase();
        System.out.println("Please enter quantity of item: ");
        int quantity = userInput.nextInt();
        for (Item item : itemList) {
            shoppingCart = item.getPrice();
            BigDecimal quantityConverted = new BigDecimal(quantity);
            shoppingCart = shoppingCart.multiply(quantityConverted);
            if ((item.getCode().equals(codeSelection))) {
                if (item.getCount() == 0) {
                    System.out.println("SOLD OUT");
                } else {
                    if ((shoppingCart.compareTo(balance) == 0) || (shoppingCart.compareTo(balance) == -1)) {
                        if (quantity <= item.getCount()) {
                            item.setCount(quantity);
                            if (item.getType().equals("Chip")) {
                                balance = balance.subtract(shoppingCart);
                                System.out.println("Vending item! Crunch, Crunch, Yum!");
                                VendingMachine.getTransactionDateTimeStamp(item.getName() + " " + item.getCode() + " " + "$" + balance.add(shoppingCart) + " " + "$" +balance);
                            } else if (item.getType().equals("Candy")) {
                                balance = balance.subtract(shoppingCart);
                                System.out.println("Vending item! Munch, Munch, Yum!");
                                VendingMachine.getTransactionDateTimeStamp(item.getName() + " " + item.getCode() + " " + "$" + balance.add(shoppingCart) + " " + "$" +balance);
                            } else if (item.getType().equals("Drink")) {
                                balance = balance.subtract(shoppingCart);
                                System.out.println("Vending item! Glug, Glug, Yum!");
                                VendingMachine.getTransactionDateTimeStamp(item.getName() + " " + item.getCode() + " " + "$" + balance.add(shoppingCart) + " " + "$" +balance);
                            } else {
                                balance = balance.subtract(shoppingCart);
                                System.out.println("Vending item! Chew, Chew, Yum!");
                                VendingMachine.getTransactionDateTimeStamp(item.getName() + " " + item.getCode() + " " + "$" + balance.add(shoppingCart) + " " + "$" +balance);
                            }
                        } else {
                            System.out.println("Sorry, item stock insufficient.");
                        }
                    } else {
                        System.out.println("Sorry, not enough money available.");
                    }
                }
            }
        }
        return balance;
    }

    public static BigDecimal finishTransaction(BigDecimal balance) {
        int quartersReturned = (balance.divide(new BigDecimal(.25)).intValue());
        BigDecimal change = balance.subtract(new BigDecimal(quartersReturned * .25));
        int dimesReturned = 0;
        VendingMachine.getTransactionDateTimeStamp("Give Change: " + "$" + balance + " " + "$0.00");
        if (change.compareTo(new BigDecimal(0.10)) >= 0) {
            dimesReturned = (change.divide(new BigDecimal(.10)).intValue());
        }

        change = change.subtract(new BigDecimal(dimesReturned * .10));
        int nickelsReturned = 0;
        if (change.compareTo(BigDecimal.ZERO) > 0) {
            nickelsReturned++;
        }
        System.out.println("quarters: " + quartersReturned);
        System.out.println("dimes: " + dimesReturned);
        System.out.println("nickels: " + nickelsReturned);
        balance = BigDecimal.ZERO;
        return balance;
    }
}
