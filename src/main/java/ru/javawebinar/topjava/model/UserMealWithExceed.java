package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }
    public LocalDate getLocalDate() {
        return dateTime.toLocalDate();
    }

    @Override
    public String toString() {
        return "Date - " + dateTime.toString()
                + "; calories " + Integer.toString(calories)
                + "; description " + description
                + "; exceed " + Boolean.toString(exceed);
    }
}
