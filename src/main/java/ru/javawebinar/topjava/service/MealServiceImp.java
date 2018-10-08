package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.model.ModelDB;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServiceImp implements MealService {

    private final int caloriesPerDay = 2000;

    @Override
    public void addMeal(Meal meal) {
        ModelDB.addMeal(meal);
    }

    @Override
    public Iterable<MealWithExceed> getAll() {
        return MealsUtil.getFilteredWithExceeded(ModelDB.getAll(), caloriesPerDay);
    }

    @Override
    public Meal getMealByID(long id) {
        return ModelDB.getById(id);
    }

    @Override
    public void updateMeal(Meal meal) {
        ModelDB.updateMeal(meal);
    }

    @Override
    public void deleteMeal(long id) {
        ModelDB.deleteMeal(id);
    }
}
