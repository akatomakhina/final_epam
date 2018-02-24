package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.controller.handling.PaymentValidator;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class PaymentAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(PaymentAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, DAOException {
        Logger.debug("is authorized: {}, is param 'itemList' not null {}", isAuthorized(request), request.getSession().getAttribute("items") != null);
        View view;
        View redirectToCheck = new View("check", ActionConstant.REDIRECT);
        if (isAuthorized(request) && (request.getSession().getAttribute("itemList") != null)) {

            int idUser = (int) request.getSession().getAttribute("userId");
            List<ProductItem> productItemList = (ArrayList<ProductItem>) request.getSession().getAttribute("itemList");

            PaymentValidator paymentValidator = new PaymentValidator();
            Order order;
            if (request.getParameter("byBalance") != null) {
                order = ActionConstant.ORDER_SERVICE.createOrder(productItemList, idUser, ActionConstant.PAY_BY_BALANCE);
                successTemplate(request, order);
                return redirectToCheck;
            }
            if (paymentValidator.execute(request)) {
                order = ActionConstant.ORDER_SERVICE.createOrder(productItemList, idUser, ActionConstant.PAY_BY_CARD);
                successTemplate(request, order);
                view = redirectToCheck;
            } else {
                Logger.info("Cannot process payment");
                view = new View("payment");
            }
        } else {
            Logger.info("Attempt to obtain unauthorized access");
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }

    private void successTemplate(HttpServletRequest request, Order order) {
        request.getSession().setAttribute("itemList", null);
        request.getSession().setAttribute("orderID", order.getId());
        Logger.info("Payment successful");
    }
}
