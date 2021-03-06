package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.entity.Catalog;
import by.katomakhina.epam.entity.Product;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowProductAction extends ActionImpl {
    private static final Logger Logger = LogManager.getLogger(ShowProductAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String param = request.getParameter("id");
        View view;
        if (param != null) {
            Integer idProduct = Integer.parseInt(param);
            Product product = ActionConstant.PRODUCT_SERVICE.findById(idProduct);
            int amount = ActionConstant.PRODUCT_SERVICE.findAmountByProductId(idProduct);
            request.setAttribute("quantityNum", amount);
            request.setAttribute("product", product);
            if (isAdmin(request)) {
                List<Catalog> catalogs = ActionConstant.CATEGORY_SERVICE.getCatalogTree();
                request.setAttribute("categories", catalogs);
            }
            Logger.info("successful forward to product-page");
            view = new View("product-page");
        } else {
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
