package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
//@RequestMapping("/meals")
public class JspMealController {

    private final MealRestController mealController;

    private LocalDate currentStartDate;
    private LocalDate currentEndDate;
    private LocalTime currentStartTime;
    private LocalTime currentEndTime;

    private void addFilterAttributes(Model model){
        model.addAttribute("startDate", currentStartDate);
        model.addAttribute("startTime", currentStartTime);
        model.addAttribute("endDate",   currentEndDate);
        model.addAttribute("endTime",   currentEndTime);
    }

    @Autowired
    public JspMealController(MealRestController mealController) {
        this.mealController = mealController;
    }

    @PostMapping("/meals/add")
    public String addMeal(@RequestParam(name = "dateTime") LocalDateTime dateTime,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "calories") Integer calories) {

        return "redirect:meals";
    }

    @PostMapping("/meals/update")
    public String addMeal(@RequestParam(name = "id") Integer id,
                          @RequestParam(name = "dateTime") String dateTime,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "calories") Integer calories) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

        if (id != null && localDateTime != null && description != null && calories != null) {
            Meal meal = new Meal(
                    localDateTime,
                    description,
                    calories);

            mealController.update(meal, id);
        }
        else if (localDateTime != null && description != null && calories != null) {
            Meal meal = new Meal(
                    localDateTime,
                    description,
                    calories);

            mealController.create(meal);
        }

        return "redirect:/meals";
    }

    @PostMapping("/meals/filter")
    public String postFunction(@RequestParam(name = "startDate") String startDate,
                               @RequestParam(name = "endDate") String endDate,
                               @RequestParam(name = "startTime") String startTime,
                               @RequestParam(name = "endTime") String endTime,
                               Model model) {

        currentStartDate = startDate != null && !startDate.isEmpty() ? LocalDate.parse(startDate) : null;
        currentEndDate   = endDate != null && !endDate.isEmpty() ? LocalDate.parse(endDate) : null;
        currentStartTime = startTime != null && !startTime.isEmpty() ? LocalTime.parse(startTime) : null;
        currentEndTime   = endTime != null && !endTime.isEmpty() ? LocalTime.parse(endTime) : null;

        addFilterAttributes(model);
        model.addAttribute("meals", mealController.getBetween(currentStartDate, currentStartTime, currentEndDate, currentEndTime));
        return "meals";
    }

    @GetMapping("/meals/delete/{id}")
    public String deleteMeal(@PathVariable(name = "id") Integer id, Model model) {
        mealController.delete(id);
        addFilterAttributes(model);
        return "redirect:/meals";
    }

    @GetMapping("/meals/create")
    public String createMeal(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        model.addAttribute("create", true);
        return "mealForm";

    }

    @GetMapping("/meals/update/{id}")
    public String updateMeal(@PathVariable(name = "id") Integer id, Model model) {
        Meal meal = mealController.get(id);
        model.addAttribute("meal", meal);
        model.addAttribute("create", false);
        return "mealForm";

    }

    @GetMapping("/meals")
    public String getFunction(Model model) {
        addFilterAttributes(model);
        if (currentStartDate != null || currentStartTime != null ||
            currentEndDate != null || currentEndTime != null) {
            model.addAttribute("meals", mealController.getBetween(currentStartDate, currentStartTime, currentEndDate, currentEndTime));
        }
        else {
            model.addAttribute("meals", mealController.getAll());
        }

        return "meals";
    }
}
