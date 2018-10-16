package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal create(Meal meal, int userID) {
        return repository.save(meal,userID);
    }

    @Override
    public void delete(int id, int userID) throws NotFoundException {
        boolean result = repository.delete(id, userID);

        if (!result){
            throw new NotFoundException(String.format("Meal with id %d not found", id));
        }
    }

    @Override
    public Meal get(int id, int userID) throws NotFoundException {
        Meal meal = repository.get(id, userID);
        if (meal == null){
            throw new NotFoundException(String.format("Meal with id %d not found", id));
        }
        return meal;
    }

    @Override
    public void update(Meal meal, int userID) {
        repository.save(meal, userID);
    }

    @Override
    public List<Meal> getAll(int userID, LocalDate dateFrom, LocalDate dateTo) {
        return repository.getAll(userID, dateFrom, dateTo);
    }
}