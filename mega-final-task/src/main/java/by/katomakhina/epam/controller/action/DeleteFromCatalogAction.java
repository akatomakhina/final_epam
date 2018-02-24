package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteFromCatalogAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(DeleteFromCatalogAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, DAOException {
        View view;
        View redirectToReferrer = new View(getReferrerName(request), ActionConstant.REDIRECT);
        if (isAdmin(request) && isInteger(request, "productId")) {
            Integer idProduct = Integer.parseInt(request.getParameter("productId"));
            ActionConstant.PRODUCT_SERVICE.deleteProduct(idProduct);
            request.getSession().setAttribute("deleteSuccess", "Delete successful");
            Logger.info("delete from catalog successfully");
            if (request.getParameter("fromProductPage") == null) {
                view = redirectToReferrer;
            } else {
                view = new View("products", ActionConstant.REDIRECT);
            }
        } else {
            view = redirectToReferrer;
        }
        return view;
    }
}
