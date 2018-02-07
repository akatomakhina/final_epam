package by.katomakhina.epam.controller.filter;

import by.katomakhina.epam.entity.User;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.user.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ProfileFilter", urlPatterns = "/do/profile")
public class ProfileFilter implements Filter {

    private static final Logger Logger = LogManager.getLogger(ProfileFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        doFilter(request, response, chain);
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String param = request.getParameter("id");
        if (param == null) {
            chain.doFilter(request, response);
            Logger.info("filtered successfully");
            return;
        }
        try {
            UserServiceImpl userService = new UserServiceImpl();
            int id = Integer.parseInt(param);
            User user = userService.getUserById(id);

            if (user.getId() == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
                Logger.error("404 error sent");

            } else {
                chain.doFilter(request, response);
                Logger.info("filtered successfully");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
