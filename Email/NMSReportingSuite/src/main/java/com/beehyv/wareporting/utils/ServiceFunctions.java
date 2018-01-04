package com.beehyv.wareporting.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by beehyv on 1/6/17.
 */
public class ServiceFunctions {

    public static String StReplace(String abc) {
        abc = abc.replaceAll("[^\\w]", "_");
        return abc;
    }

    public static String getMonthYear(Date toDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(toDate);
        c.add(Calendar.MONTH, -1);
        int month = c.get(Calendar.MONTH) + 1;
        int year = (c.get(Calendar.YEAR)) % 100;
        String monthString;
        if (month < 10) {
            monthString = "0" + String.valueOf(month);
        } else monthString = String.valueOf(month);

        String yearString = String.valueOf(year);

        return monthString + "_" + yearString;
    }

}
