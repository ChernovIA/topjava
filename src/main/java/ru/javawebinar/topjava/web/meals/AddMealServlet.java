package ru.javawebinar.topjava.web.meals;

import ru.javawebinar.topjava.controller.ServiceController;
import ru.javawebinar.topjava.controller.ServiceControllerImp;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.DAO.ModelDB;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;

@WebServlet(urlPatterns = "/meals/add")
public class AddMealServlet extends HttpServlet {

    private static final Logger log = getLogger(AddMealServlet.class);
    private ServiceController controller;

    @Override
    public void init() throws ServletException {
        log.debug("AddMealServlet init");
        controller = ServiceControllerImp.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.debug("AddMealServlet post");
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        LocalDateTime dateTime = LocalDateTime.parse(date+" "+time, TimeUtil.getFormatter());

        String description = request.getParameter("description");

        String calories = request.getParameter("calories");

        Meal newMeal = new Meal(ModelDB.getCurrentID().getAndIncrement(),dateTime, description, Integer.parseInt(calories));
        controller.addMeal(newMeal);

        log.debug("AddMealServlet redirect to meals");
        response.sendRedirect("../meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("AddMealServlet get");

        log.debug("AddMealServlet forward to meals");
        request.getRequestDispatcher("AddMeal.jsp").forward(request,response);
    }
}
