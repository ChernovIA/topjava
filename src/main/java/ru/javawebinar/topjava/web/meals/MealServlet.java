package ru.javawebinar.topjava.web.meals;

import org.slf4j.Logger;
import ru.javawebinar.topjava.controller.ServiceController;
import ru.javawebinar.topjava.controller.ServiceControllerImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(urlPatterns = "/meals")
public class MealServlet extends HttpServlet {

    private ServiceController controller;

    @Override
    public void init() throws ServletException {
        log.debug("MealServlet init");
        controller = ServiceControllerImp.getInstance();
    }

    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("MealServlet get");

        req.setAttribute("mealsList", controller.getAll());

        log.debug("MealServlet forward to meals");
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

}
