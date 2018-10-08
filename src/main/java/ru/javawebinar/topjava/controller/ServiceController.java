package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

public interface ServiceController {

    Iterable<MealWithExceed> getAll();

    void addMeal(Meal meal);

    void updateMeal(Meal meal);

    void deleteMeal(long id);

    Meal getMeal(long id);
}
