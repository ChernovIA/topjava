package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public List<MealWithExceed> getAll(Map<String, LocalDate> filterDate, Map<String, LocalTime> filterTime) {
        log.info("getAll");

        return MealsUtil.getFilteredWithExceeded(service.getAll(SecurityUtil.authUserId(),
                filterDate.get("dateFrom"), filterDate.get("dateTo")), SecurityUtil.authUserCaloriesPerDay(),
                filterTime.get("timeFrom"), filterTime.get("timeTo"));
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public MealWithExceed create(Meal meal) {
        log.info("create {}", meal);
        ValidationUtil.checkNew(meal);
        return MealsUtil.createWithExceed(service.create(meal, SecurityUtil.authUserId()), false);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, meal.getId());
        ValidationUtil.assureIdConsistent(meal, SecurityUtil.authUserId());
        service.update(meal, SecurityUtil.authUserId());
    }

}