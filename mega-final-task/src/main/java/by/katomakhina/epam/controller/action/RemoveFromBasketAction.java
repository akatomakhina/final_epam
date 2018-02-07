package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.product.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveFromBasketAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(RemoveFromBasketAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View view;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            ProductServiceImpl service = new ProductServiceImpl();
            service.removeBasketItemById(id);
            request.getSession().setAttribute("deleteSuccess", "successful delete from cart");
            Logger.info("successful delete from cart");
            view = new View("cart", ActionConstant.REDIRECT);

        } catch (NumberFormatException e) {
            Logger.warn("Cannot delete item from cart", e);
            request.getSession().setAttribute("deleteFail", "Error: cannot delete from cart");
            return new View("cart");

        }
        return view;
    }
}
