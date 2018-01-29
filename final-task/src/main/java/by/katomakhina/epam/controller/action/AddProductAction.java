package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
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
        int userId = (int) request.getSession().getAttribute("idUser");
        int ProductId = Integer.parseInt(request.getParameter("idProduct"));
        int addQuantity;
        try {
            addQuantity = Integer.parseInt(request.getParameter("addAmount"));
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("invalidAmountAdd", "you should set number less than quantity in warehouse. But avoid using negative numbers and zero");
            Logger.warn("cannot add item to cart because of non digit chars in 'amount' form");
            return redirectToReferrer;
        }
        HttpSession session = request.getSession();
        Logger.info(getReferrerName(request));
        ProductServiceImpl service = new ProductServiceImpl();
        if (service.isAmountValid(addQuantity, ProductId)) {
            service.addProductToCart(userId, ProductId, addQuantity);
            session.setAttribute("addMessage", "Product added successful");
            Logger.info("product item added to the basket");

        } else {
            session.setAttribute("invalidAmountAdd", "you should set number less than amount in warehouse. But avoid using negative numbers and zero");
            Logger.warn("cannot add item to cart because of invalid amount");

        }
        return redirectToReferrer;
    }
}
