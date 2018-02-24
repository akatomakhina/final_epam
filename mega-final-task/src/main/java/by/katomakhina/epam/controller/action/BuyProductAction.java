package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.entity.Product;
import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class BuyProductAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(BuyProductAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View view;
        View redirectToReferrer = new View(getReferrerName(request), ActionConstant.REDIRECT);
        if (isUser(request)) {
            int idProduct = Integer.parseInt(request.getParameter("productId"));
            int buyAmount;
            try {
                buyAmount = Integer.parseInt(request.getParameter("buyQuantity"));
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("invalidQuantity", "Avoid using negative numbers and zero");
                Logger.warn("Cannot purchase single item because of invalid amount");
                return redirectToReferrer;
            }
            int userId = (int) request.getSession().getAttribute("userId");
            List<ProductItem> productItemList = new ArrayList<>();
            Product product = ActionConstant.PRODUCT_SERVICE.findById(idProduct);
            ProductItem productItem = new ProductItem(product, buyAmount);
            productItemList.add(productItem);
            if (ActionConstant.PRODUCT_SERVICE.isAmountValid(buyAmount, idProduct)) {
                buySuccessTemplate(request, productItemList, userId);
                view = new View("payment");

            } else {
                HttpSession session = request.getSession();
                session.setAttribute("invalidQuantity", "You should set number less than amount in warehouse. But avoid using negative numbers and zero");
                Logger.warn("Cannot purchase single item because of invalid amount");
                view = redirectToReferrer;
            }

        } else {
            view = ActionConstant.REDIRECT_TO_HOME;

        }
        return view;
    }
}
