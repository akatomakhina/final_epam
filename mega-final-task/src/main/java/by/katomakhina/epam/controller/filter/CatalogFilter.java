package by.katomakhina.epam.controller.filter;

import by.katomakhina.epam.controller.action.EditBasketAction;
import by.katomakhina.epam.entity.Subcatalog;
import by.katomakhina.epam.service.catalog.impl.CatalogServiceImpl;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CatalogFilter", urlPatterns = "/nastichka/products")
public class CatalogFilter implements Filter {

    private static final Logger Logger = LogManager.getLogger(CatalogFilter.class);

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
        String param = request.getParameter("catalog");
        if (param == null) {
            chain.doFilter(request, response);
            Logger.info("Filtered successfully");
            return;
        }
        try {
            CatalogServiceImpl catalogService = new CatalogServiceImpl();
            int idCatalog = Integer.parseInt(param);
            Subcatalog subcategory = catalogService.getSubcatalogById(idCatalog);
            if (subcategory.getId() == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            } else {
                chain.doFilter(request, response);
                Logger.info("Filtered successfully");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");

        } catch (ServiceException e) {
            Logger.info("Filtered successfully");
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }

}
