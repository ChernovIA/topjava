package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

public interface MealService {

    Iterable<MealWithExceed> getAll();

    Meal getMealByID(long id);

    void addMeal(Meal meal);

    void updateMeal(Meal meal);

    void deleteMeal(long id);
}
