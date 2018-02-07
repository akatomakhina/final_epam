package by.katomakhina.epam.controller.filter;

import by.katomakhina.epam.controller.action.EditBasketAction;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.order.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CheckFilter", urlPatterns = "/do/check")
public class CheckFilter implements Filter {
    private static final Logger Logger = LogManager.getLogger(CheckFilter.class);

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
        try {
            OrderServiceImpl orderService = new OrderServiceImpl();
            int id = Integer.parseInt(param);
            Order order = orderService.getOrderById(id);

            if (order.getId() == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            } else {
                Logger.info("filtered successful");
                chain.doFilter(request, response);
            }
        } catch (NumberFormatException e) {
            if (request.getSession().getAttribute("idOrder") == null) {
                response.sendRedirect("home-page");
                return;
            }
            int idOrder = (int) request.getSession().getAttribute("idOrder");
            if (idOrder != 0) {
                request.setAttribute("id", idOrder);
                request.getSession().setAttribute("idOrder", null);
                chain.doFilter(request, response);
                Logger.info("filtered successful");
                return;
            }
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
        } catch (ServiceException e) {
            Logger.info("filtered unsuccessful");
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
