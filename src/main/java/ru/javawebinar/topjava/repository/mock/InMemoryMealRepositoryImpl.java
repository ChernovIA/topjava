package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userID) {

        if (meal.isNew()) {
            meal.setUserId(userID);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        else {
            if (meal.getUserId() != userID){
                return null;
            }
        }

        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userID) {
        Meal meal = repository.get(id);
        if (mealInhereUser(meal, userID)){
            return repository.keySet().removeIf(curId -> curId == id);
        }
        return false;
    }

    @Override
    public Meal get(int id, int userID) {
        Meal meal = repository.get(id);
        if (mealInhereUser(meal, userID)){
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userID, LocalDate dateFrom, LocalDate dateTo) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userID && DateTimeUtil.isBetweenDate(meal.getDate(),dateFrom,dateTo))
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }

    private boolean mealInhereUser(Meal meal, int userId){
        return meal != null && meal.getUserId() == userId;
    }
}

