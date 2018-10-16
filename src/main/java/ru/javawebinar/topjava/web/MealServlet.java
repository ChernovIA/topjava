package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController controller;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");

        String userId = request.getParameter("userId");

        if (userId != null){
            SecurityUtil.setAuthUserId(Integer.parseInt(userId));
            response.sendRedirect("index.html");
        }
        else {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            controller.update(meal);
            response.sendRedirect("meals");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String dateFromStr = request.getParameter("dateFrom");
        String dateToStr   = request.getParameter("dateTo");
        String timeFromStr = request.getParameter("timeFrom");
        String timeToStr   = request.getParameter("timeTo");

        LocalDate dateFrom = dateFromStr == null || dateFromStr.isEmpty()? LocalDate.MIN : LocalDate.parse(dateFromStr);
        LocalDate dateTo   = dateToStr   == null || dateToStr.isEmpty()? LocalDate.MAX : LocalDate.parse(dateToStr);
        LocalTime timeFrom = timeFromStr == null || timeFromStr.isEmpty()? LocalTime.MIN : LocalTime.parse(timeFromStr);
        LocalTime timeTo   = timeToStr   == null || timeToStr.isEmpty()? LocalTime.MAX : LocalTime.parse(timeToStr);

        Map<String, LocalDate> filterDate = new HashMap<>(2);
        Map<String, LocalTime> filterTime = new HashMap<>(2);

        filterDate.put("dateFrom", dateFrom);
        filterDate.put("dateTo", dateTo);

        filterTime.put("timeFrom", timeFrom);
        filterTime.put("timeTo", timeTo);


        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) : controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", controller.getAll(filterDate, filterTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        if (appCtx != null){
            appCtx.close();
        }
    }
}
