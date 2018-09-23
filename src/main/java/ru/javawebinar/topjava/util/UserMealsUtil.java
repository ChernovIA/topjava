package ru.javawebinar.topjava.util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import javax.sql.DataSource;
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
        List<UserMealWithExceed> list = getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
        //list.get(0).setExceed(true);
        for(UserMealWithExceed userMealWithExceed: list){
            System.out.println(userMealWithExceed.toString());
        }
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapMax =
                mealList.stream().collect(Collectors.groupingBy(UserMeal::getLocalDate,Collectors.summingInt(UserMeal::getCalories)));

        final LocalDate[] currentDate = new LocalDate[]{mealList.get(0).getLocalDate()};
        final Integer[] maxInDay      = new Integer[]{0};
        final UserMealWithExceed[] lastAdded = new UserMealWithExceed[1];
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
                            UserMealWithExceed userMealWithExceed = null;
                            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                                userMealWithExceed =
                                        new UserMealWithExceed(userMeal.getDateTime(),
                                                userMeal.getDescription(), userMeal.getCalories(),
                                                exceed);
                                lastAdded[0] = userMealWithExceed;
                            }
                            maxInDay[0] += userMeal.getCalories();
                            exceed[0] = maxInDay[0] > caloriesPerDay;
                            return userMealWithExceed;}
                            ).filter(userMealWithExceed -> (userMealWithExceed != null)).collect(Collectors.toList());

     }

//    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
//
//        Map<LocalDate, Integer> summes = new HashMap<>();
//        List<UserMealWithExceed> mealWithExceeds = new LinkedList<>();
//        for(UserMeal userMeal: mealList) {
//
//            LocalDate currentDate = userMeal.getDateTime().toLocalDate();
//
//            int currentSum = userMeal.getCalories();
//            if (summes.containsKey(currentDate)){
//                currentSum += summes.get(currentDate);
//            }
//            summes.put(currentDate, currentSum);
//
//        }
//
//        for (UserMeal userMeal : mealList) {
//
//            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
//                mealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(),
//                        userMeal.getDescription(), userMeal.getCalories(),
//                        summes.getOrDefault(userMeal.getDateTime().toLocalDate(),0) > caloriesPerDay));
//            }
//
//        }
//
//        return mealWithExceeds;
//    }

    public static List<UserMealWithExceed> getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> mealWithExceeds = new LinkedList<>();

        if (mealList.size() == 0){
            return mealWithExceeds;
        }

        LocalDate currentDate = mealList.get(0).getLocalDate();
        Boolean[] exceed = new Boolean[]{false};
        int maxInDay = 0;
        for (UserMeal userMeal : mealList) {

            if (!currentDate.equals(userMeal.getLocalDate())){
                exceed =  new Boolean[]{false};
                maxInDay = 0;
                currentDate = userMeal.getLocalDate();
            }

            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(), userMeal.getCalories(),
                        exceed));
            }
            maxInDay += userMeal.getCalories();
            exceed[0] = maxInDay > caloriesPerDay;
        }

        return mealWithExceeds;
    }

}
