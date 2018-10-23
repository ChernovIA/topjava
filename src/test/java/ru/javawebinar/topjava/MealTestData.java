package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ+2;

    public static final Meal MEAL1 = new Meal(MEAL_ID,
            LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL1UPDATE = new Meal(MEAL_ID,
            LocalDateTime.of(2019, Month.MAY, 30, 10, 0), "Завтрак", 500);

    public static final Meal MEAL2 = new Meal(MEAL_ID + 1,
            LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Обед", 510);
    public static final Meal MEAL3 = new Meal(MEAL_ID + 2,
            LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Ужин", 1000);

    public static void assertMatchMeal(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "");
    }
}