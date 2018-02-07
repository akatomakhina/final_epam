package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.controller.handling.ProductFormValidator;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.Product;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateProductAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(UpdateProductAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, DAOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        ProductFormValidator validator = new ProductFormValidator();
        if (validator.execute(request, true)) {
            String title = request.getParameter("name");
            Integer price = Integer.parseInt(request.getParameter("price"));
            Integer quantity = Integer.parseInt(request.getParameter("quantity"));
            String description = request.getParameter("description");
            Integer catalog = Integer.parseInt(request.getParameter("category"));
            Product product = new Product();
            product.setId(productId);
            product.setDescription(description);
            product.setPrice(price);
            product.setTitle(title);
            product.setId_catalog(catalog);
            boolean isAffected = ActionConstant.PRODUCT_SERVICE.updateProduct(product, quantity);
            if (isAffected) {
                request.getSession().setAttribute("success", "success update");
                Logger.info("products updated successfully");
            } else {
                request.getSession().setAttribute("redundantUpdate", "update failure, you should change at least one parameter before submitting");
                Logger.info("redundant product update detected");
            }
        }

        return new View("product-page?id=" + productId, ActionConstant.REDIRECT);
    }
}