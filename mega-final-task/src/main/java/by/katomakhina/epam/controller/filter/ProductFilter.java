package by.katomakhina.epam.controller.filter;

import by.katomakhina.epam.entity.Product;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.product.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "ProductFilter", urlPatterns = "/nastichka/product-page")
public class ProductFilter implements Filter {
    private static final Logger Logger = LogManager.getLogger(ProductFilter.class);

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
            ProductServiceImpl productService = new ProductServiceImpl();
            int idProduct = Integer.parseInt(param);
            Product product = productService.findById(idProduct);

            if (product.getId() == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");


            } else {
                chain.doFilter(request, response);
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
