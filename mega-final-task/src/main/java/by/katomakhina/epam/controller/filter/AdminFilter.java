package by.katomakhina.epam.controller.filter;

import by.katomakhina.epam.controller.action.EditBasketAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/nastichka/admin-page")
public class AdminFilter implements Filter {
    private static final Logger Logger = LogManager.getLogger(AdminFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        doFilter(request, response, chain);
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            Logger.info("Session is null");
            response.sendRedirect("home-page");
            return;
        }

        if (session.getAttribute("loggedUserRole") == "user") {
            Logger.info("user session");
            response.sendRedirect("home-page");
            return;
        }
        Logger.info("admin session");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
