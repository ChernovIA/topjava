package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_MEALS_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_MEALS_URL = "/rest/meals";

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal getMeal(@PathVariable(name = "id") int id){
        return super.get(id);
    }

    @DeleteMapping(value="/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable(name = "id") int id){
        super.delete(id);
    }

    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMeal(@RequestBody Meal meal, @PathVariable(name = "id") int id){
        super.update(meal, id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createMeal(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestBody Meal meal){
        Meal newMeal = super.create(meal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_MEALS_URL + "/{id}")
                .buildAndExpand(newMeal.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(newMeal);
    }

    @GetMapping(value = "/between")
    public List<MealTo> getMealsBetween(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime){
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}