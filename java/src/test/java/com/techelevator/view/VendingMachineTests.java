package com.techelevator.view;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Scanner;

public class VendingMachineTests {

//    @Test
//    public  void feedMoney_updates_balance_as_more_bills_are_added(){
//        Scanner userInput = new Scanner(System.setIn());
//        int valueOne = userInput.nextInt();
//        userInput.nextLine();
//        int valueTwo = userInput.nextInt();
//        BigDecimal balance = new BigDecimal(0);
//        int expectedBalance = 5;
//        BigDecimal actualBalance = VendingMachine.feedMoney(balance, userInput);
//
//        Assert.assertEquals(expectedBalance, actualBalance);
//    }

    @Test
    public void finishTransaction_returns_balance_as_zero() {
        BigDecimal balance = new BigDecimal(2);
        BigDecimal expected = new BigDecimal(0);
        BigDecimal actual = VendingMachine.finishTransaction(balance);

        Assert.assertEquals(expected, actual);
    }

}
