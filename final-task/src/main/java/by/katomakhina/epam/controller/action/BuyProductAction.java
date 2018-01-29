package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
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
            int productId = Integer.parseInt(request.getParameter("idProduct"));
            int buyQuantity;
            try {
                buyQuantity = Integer.parseInt(request.getParameter("buyAmount"));
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("invalidAmount", "avoid using negative numbers and zero");
                Logger.warn("cannot purchase single item because of invalid amount");
                return redirectToReferrer;
            }
            int userId = (int) request.getSession().getAttribute("idUser");
            List<ProductItem> itemList = new ArrayList<>();
            Product product = ActionConstant.PRODUCT_SERVICE.findById(productId);
            ProductItem item = new ProductItem(product, buyQuantity);
            itemList.add(item);
            if (ActionConstant.PRODUCT_SERVICE.isAmountValid(buyQuantity, productId)) {
                buySuccessTemplate(request, itemList, userId);
                view = new View("payment");

            } else {
                HttpSession session = request.getSession();
                session.setAttribute("invalidAmount", "you should set number less than amount in warehouse. But avoid using negative numbers and zero");
                Logger.warn("cannot purchase single item because of invalid amount");
                view = redirectToReferrer;
            }

        } else {
            view = ActionConstant.REDIRECT_TO_HOME;

        }
        return view;
    }
}
