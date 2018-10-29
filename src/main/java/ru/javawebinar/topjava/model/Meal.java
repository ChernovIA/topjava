package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.GET, query = "SELECT m FROM Meal m where m.id =:id and m.user.id=:userid"), //SELECT m FROM Meal m INNER JOIN FETCH User as u on m.user.id = u.id where m.id=:id and u.id=:userid
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal u where u.id =:id and u.user.id = :userid"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m where m.user.id = :userid ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.ALL_SORTED_BETWEEN, query = "SELECT m FROM Meal m where m.user.id = :userid and m.dateTime BETWEEN :timeStart and :timeEnd ORDER BY m.dateTime DESC")
        })
@Entity
@Table(name = "meals")
public class Meal extends AbstractBaseEntity {
    public static final String GET = "Meal.get";
    public static final String DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String ALL_SORTED_BETWEEN = "Meal.getAllSortedBetween";

    @NotNull
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @NotBlank
    private String description;

    @NotNull
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
