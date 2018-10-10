package ru.javawebinar.topjava.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static DateTimeFormatter getDateFormatter(){
        return DateTimeFormatter.ofPattern(getFullDateFormat());
    }

    public static String getFullDateFormat(){
        return "yyyy-MM-dd HH:mm";
    }

    public static String getDateFormat(){
        return "yyyy-MM-dd";
    }

    public static String getTimeFormat(){
        return "HH:mm";
    }

}
