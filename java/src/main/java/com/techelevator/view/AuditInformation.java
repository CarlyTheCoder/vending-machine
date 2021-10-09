package com.techelevator.view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;


public class AuditInformation {

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
}
