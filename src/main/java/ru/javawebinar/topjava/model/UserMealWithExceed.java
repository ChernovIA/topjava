package ru.javawebinar.topjava.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public final Boolean[] exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, Boolean[] exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }
    public LocalDate getLocalDate() {
        return dateTime.toLocalDate();
    }

    public void setExceed(boolean value){
        exceed[0] = value;
    }
    @Override
    public String toString() {
        return "Date - " + dateTime.toString()
                + "; calories " + Integer.toString(calories)
                + "; description " + description
                + "; exceed " + Boolean.toString(exceed[0]);
    }
}
