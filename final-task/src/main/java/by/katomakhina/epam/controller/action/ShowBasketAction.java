package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.entity.Basket;
import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.prefs.BackingStoreException;

public class ShowBasketAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ShowBasketAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View view;
        if (isUser(request)) {
            Integer userId = (int) request.getSession().getAttribute("idUser");
            List<ProductItem> items = ActionConstant.PRODUCT_SERVICE.getBasketByUserId(userId);
            int totalAmount = ActionConstant.ORDER_SERVICE.getTotalAmount(items);
            Basket basket = ActionConstant.PRODUCT_SERVICE.createBasket(items);
            request.setAttribute("items", basket.getBasketItems());
            request.setAttribute("totalAmount", totalAmount);
            if (items.isEmpty()) {
                request.setAttribute("emptyMessage", "There are no items in the basket");
                Logger.info("There are no items in the basket");
            }
            view = new View("basket");

        }else if (!isAuthorized(request)){
            view = new View("login-page", ActionConstant.REDIRECT);
        }else {
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
