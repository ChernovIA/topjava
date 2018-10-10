package ru.javawebinar.topjava.web.meals;

import ru.javawebinar.topjava.controller.ServiceController;
import ru.javawebinar.topjava.controller.ServiceControllerImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;

@WebServlet(urlPatterns = "/meals/delete")
public class DeleteMealServlet extends HttpServlet {

    private static final Logger log = getLogger(DeleteMealServlet.class);

    private ServiceController controller;

    @Override
    public void init() throws ServletException {
        log.debug("DeleteMealServlet init");
        controller = ServiceControllerImp.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("DeleteMealServlet get");

        Long id = Long.parseLong(request.getParameter("id"));

        controller.deleteMeal(id);

        log.debug("DeleteMealServlet redirect to meals");
        response.sendRedirect("../meals");
    }
}
