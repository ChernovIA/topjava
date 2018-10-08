package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static DateTimeFormatter getFormatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public static DateTimeFormatter getFormatterDate(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public static DateTimeFormatter getFormatterTime(){
        return DateTimeFormatter.ofPattern("HH:mm");
    }

}
