package ru.javawebinar.topjava.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;

@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {

    private static final Logger log = getLogger(EncodingFilter.class);

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        log.debug("EncodingFilter filtered");
        req.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
    }

}
