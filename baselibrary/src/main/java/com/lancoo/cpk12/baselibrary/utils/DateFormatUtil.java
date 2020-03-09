package com.lancoo.cpk12.baselibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtil {

    private static SimpleDateFormat mSimpleFormat = null;

    static {
        mSimpleFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    public static String format(String time) {
        if (time == null) return "";
        if (!time.contains("T")) {
            if (time.contains("/")) time.replaceAll("/", "-");
            return time;//xwt

        }
        String[] array = time.split("T");
        String date = array[0];
        time = array[1];
        time = time.substring(0, time.lastIndexOf(":"));
        if (date.contains("/")) date.replaceAll("/", "-");
        return date + " " + time;
    }

    public static String complexFormat(String time) {
        //return format(time);
        if (time == null) return "";
        if (!time.contains("T")) return time;//xwt
        String currentDate = mSimpleFormat.format(new Date());
        String[] array = currentDate.split("-");

        int currentYear = Integer.valueOf(array[0]);
        int currentMonth = Integer.valueOf(array[1]);
        int currentDay = Integer.valueOf(array[2]);

        array = time.split("T");
        String date = array[0];
        time = array[1];
        time = time.substring(0, time.lastIndexOf(":"));

        array = date.split("-");
        int year = Integer.valueOf(array[0]);
        int month = Integer.valueOf(array[1]);
        int day = Integer.valueOf(array[2]);

        if (year == currentYear) {
            if (month == currentMonth && day == currentDay)
                return time;
            else
                return month + "-" + day + " " + time;
        } else
            return date + " " + time;
    }

    public static String getDate(String time) {
        String[] array = time.split("T");
        return array[0];
    }

}
