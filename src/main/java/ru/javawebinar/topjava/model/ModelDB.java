package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ModelDB {
    private static AtomicLong currentID = new AtomicLong(0);

    private static ConcurrentHashMap<Long, Meal> tableMeal;

    static {
        tableMeal = new ConcurrentHashMap<>();
        try {
            long id = ModelDB.getCurrentID().getAndIncrement();
            Meal meal = new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
            addMeal(meal);

            id = ModelDB.getCurrentID().getAndIncrement();
            meal = new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
            addMeal(meal);

            id = ModelDB.getCurrentID().getAndIncrement();
            meal = new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
            addMeal(meal);

            id = ModelDB.getCurrentID().getAndIncrement();
            meal = new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
            addMeal(meal);

            id = ModelDB.getCurrentID().getAndIncrement();
            meal = new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
            addMeal(meal);

            id = ModelDB.getCurrentID().getAndIncrement();
            meal = new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
            addMeal(meal);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static AtomicLong getCurrentID() {
        return currentID;
    }

    public static List<Meal> getAll(){
        return Collections.list(tableMeal.elements());
    }

    public static synchronized void addMeal(Meal meal){
        tableMeal.put(meal.getId(), meal);
    }

    public static synchronized void updateMeal(Meal meal){
        tableMeal.put(meal.getId(), meal);
    }

    public static void deleteMeal(Long id){
        tableMeal.remove(id);
    }

    public static Meal getById(long id){
        return tableMeal.get(id);
    }
}
