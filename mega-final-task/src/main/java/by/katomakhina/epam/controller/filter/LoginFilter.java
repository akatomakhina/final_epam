package by.katomakhina.epam.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/nastichka/login-page")
public class LoginFilter implements Filter {
    private static final Logger Logger = LogManager.getLogger(LoginFilter.class);

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
        HttpSession session = request.getSession(false);
        Logger.info("LoginFilter filter starts");
        if (session != null) {
            if (session.getAttribute("idUser") != null) {
                Logger.info("LoginFilter tries to send redirect");
                response.sendRedirect("profile");
                return;
            }
        }
        chain.doFilter(request, response);
        Logger.info("request is filtered");
    }

    @Override
    public void destroy() {

    }
}
