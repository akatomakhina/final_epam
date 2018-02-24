package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.product.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddProductAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(AddProductAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View redirectToReferrer = new View(getReferrerName(request), ActionConstant.REDIRECT);
        int idUser = (int) request.getSession().getAttribute("userId");
        int idProduct = Integer.parseInt(request.getParameter("productId"));
        int addAmount;
        try {
            addAmount = Integer.parseInt(request.getParameter("addQuantity"));
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("invalidQuantityAdd", "You should set number less than amount in warehouse. But avoid using negative numbers and zero");
            Logger.warn("Cannot add item to basket because of non digit chars in 'amount' form");
            return redirectToReferrer;
        }
        HttpSession session = request.getSession();
        Logger.info(getReferrerName(request));
        ProductServiceImpl service = new ProductServiceImpl();
        if (service.isAmountValid(addAmount, idProduct)) {
            service.addProductToBasket(idUser, idProduct, addAmount);
            session.setAttribute("addMessage", "Product added successful");
            Logger.info("Product item added to the basket");

        } else {
            session.setAttribute("invalidAmountAdd", "You should set number less than amount in warehouse. But avoid using negative numbers and zero");
            Logger.warn("Cannot add item to basket because of invalid amount");

        }
        return redirectToReferrer;
    }
}
