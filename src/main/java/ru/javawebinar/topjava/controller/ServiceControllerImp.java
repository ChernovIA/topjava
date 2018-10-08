package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImp;

public class ServiceControllerImp implements ServiceController {

    private static volatile ServiceController instance;

    MealService mealService;

    private ServiceControllerImp(){
        mealService = new MealServiceImp();
    }

    public static ServiceController getInstance() {
        ServiceController localInstance = instance;
        if (localInstance == null) {
            synchronized (ServiceController.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ServiceControllerImp();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Iterable<MealWithExceed> getAll() {
        return mealService.getAll();
    }

    @Override
    public void addMeal(Meal meal) {
        mealService.addMeal(meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealService.updateMeal(meal);
    }

    @Override
    public void deleteMeal(long id) {
        mealService.deleteMeal(id);
    }

    @Override
    public Meal getMeal(long id) {
        return mealService.getMealByID(id);
    }
}
