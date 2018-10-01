package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.model.UserMealWithExceedStreamAd;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        //List<UserMealWithExceed> list = getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        //List<UserMealWithExceedStreamAd> list = getFilteredWithExceededStreamAd(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        for(UserMealWithExceed userMealWithExceed: list){
            System.out.println(userMealWithExceed.toString());
        }
    }

    //Костылеорентированное создание колекции за один проход! //optional2
    public static List<UserMealWithExceedStreamAd> getFilteredWithExceededStreamAd(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final LocalDate[] currentDate = new LocalDate[]{mealList.get(0).getLocalDate()};
        final Integer[] maxInDay      = new Integer[]{0};
        final UserMealWithExceedStreamAd[] lastAdded = new UserMealWithExceedStreamAd[1];
        return mealList.stream().sorted((o1, o2) -> (o1.getLocalDate().compareTo(o2.getLocalDate())))
                .map(userMeal -> {
                            Boolean[] exceed;
                            if (!currentDate[0].equals(userMeal.getLocalDate())){
                                exceed =  new Boolean[]{false};
                                maxInDay[0] = 0;
                                currentDate[0] = userMeal.getLocalDate();
                            }
                            else {
                                if (lastAdded[0] != null) {
                                    exceed = lastAdded[0].exceed;
                                }
                                else{
                                    exceed =  new Boolean[]{false};
                                }
                            }
                            UserMealWithExceedStreamAd userMealWithExceed = null;
                            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                                userMealWithExceed =
                                        new UserMealWithExceedStreamAd(userMeal.getDateTime(),
                                                userMeal.getDescription(), userMeal.getCalories(),
                                                exceed);
                                lastAdded[0] = userMealWithExceed;
                            }
                            maxInDay[0] += userMeal.getCalories();
                            exceed[0] = maxInDay[0] > caloriesPerDay;
                            return userMealWithExceed;}
                            ).filter(userMealWithExceed -> (userMealWithExceed != null)).collect(Collectors.toList());

    }

    //создание коллекции стримами //optional
    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapMax =
                mealList.stream().collect(Collectors.groupingBy(UserMeal::getLocalDate,Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> {UserMealWithExceed userMealWithExceed =
                        new UserMealWithExceed(userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                mapMax.getOrDefault(userMeal.getLocalDate(),0) > caloriesPerDay);
                    return userMealWithExceed;}).collect(Collectors.toList());
    }

    //создание коллекции циклами
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapMax = new HashMap<>();
        List<UserMealWithExceed> mealWithExceeds = new LinkedList<>();

        for(UserMeal userMeal: mealList) {

            LocalDate currentDate = userMeal.getDateTime().toLocalDate();

            int currentSum = userMeal.getCalories();
            if (mapMax.containsKey(currentDate)){
                currentSum += mapMax.get(currentDate);
            }
            mapMax.put(currentDate, currentSum);

        }

        for (UserMeal userMeal : mealList) {

            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(), userMeal.getCalories(),
                        mapMax.getOrDefault(userMeal.getDateTime().toLocalDate(),0) > caloriesPerDay));
            }

        }

        return mealWithExceeds;
    }

}
