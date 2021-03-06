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

public class NewProductAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(NewProductAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, DAOException {
        View view;
        View redirectToCatalogManager = new View("catalog-manager", ActionConstant.REDIRECT);
        if (isAdmin(request)) {
            ProductFormValidator validator = new ProductFormValidator();
            if (validator.execute(request, false)) {
                Integer amount = 0;
                Product product = new Product();
                if (!(request.getParameter("quantity").equals(""))) {
                    amount = Integer.parseInt(request.getParameter("quantity"));
                }
                product.setTitle(request.getParameter("name").trim());
                product.setId_catalog(Integer.parseInt(request.getParameter("category")));
                product.setPrice(Integer.parseInt(request.getParameter("price")));
                product.setDescription(request.getParameter("description"));
                product.setVendor(request.getParameter("vendor"));
                ActionConstant.PRODUCT_SERVICE.createProduct(product, amount);
                request.getSession().setAttribute("successUpdate", "Product added successfully");
                Logger.info("product entry added successfully");
                view = redirectToCatalogManager;
            } else {
                Logger.info("cannot create new product entry");
                view = redirectToCatalogManager;
            }

        } else {
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
