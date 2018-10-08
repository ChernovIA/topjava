package ru.javawebinar.topjava.web.meals;

import ru.javawebinar.topjava.controller.ServiceController;
import ru.javawebinar.topjava.controller.ServiceControllerImp;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = "/meals/update")
public class UpdateMealServlet extends HttpServlet {

    private ServiceController controller;

    @Override
    public void init() throws ServletException {
        controller = ServiceControllerImp.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id  = request.getParameter("id");
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        LocalDateTime dateTime = LocalDateTime.parse(date+" "+time, TimeUtil.getFormatter());

        String description = request.getParameter("description");

        String calories = request.getParameter("calories");

        Meal newMeal = new Meal(Long.parseLong(id),dateTime, description, Integer.parseInt(calories));
        controller.updateMeal(newMeal);

        response.sendRedirect("../meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id  = request.getParameter("id");
        Meal meal = controller.getMeal(Long.parseLong(id));
        MealWithExceed mealWithExceed = MealsUtil.createWithExceed(meal,false);

        request.setAttribute("meal", mealWithExceed);

        request.getRequestDispatcher("../mealEdit.jsp").forward(request,response);
    }
}
