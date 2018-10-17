package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalTime;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int UserID);

    boolean delete(int id, int UserID);

    Meal get(int id, int UserID);

    List<Meal> getAll(int UserID, LocalTime dateFrom, LocalTime dateTo);
}
