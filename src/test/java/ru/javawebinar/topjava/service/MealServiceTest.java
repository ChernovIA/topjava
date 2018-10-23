package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL1UPDATE;
import static ru.javawebinar.topjava.MealTestData.MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {

    @Autowired
    MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, ADMIN_ID);
        Assert.assertEquals(meal.getId(), MEAL1.getId());
    }

    @Test(expected = NotFoundException.class)
    public void getNotUserMeal() {
        Meal meal = service.get(MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNPE() {
        Meal meal = service.get(MEAL_ID-100, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNPE() {
        service.delete(MEAL_ID, USER_ID);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        Assert.assertEquals(2,meals.size());
    }

    @Test(expected = NotFoundException.class)
    public void update() {
        service.update(MEAL1UPDATE, USER_ID);
    }

}