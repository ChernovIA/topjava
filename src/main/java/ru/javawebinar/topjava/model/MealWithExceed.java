package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealWithExceed {

    private long id;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    public void setExceed(boolean exceed) {
        this.exceed = exceed;
    }

    public String getFormatDateTime(){
        return dateTime.format(TimeUtil.getFormatter());
    }

    public String getFormatDate(){
        return dateTime.format(TimeUtil.getFormatterDate());
    }

    public String getFormatTime(){
        return dateTime.format(TimeUtil.getFormatterTime());
    }

    private boolean exceed;

    public MealWithExceed(long id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}