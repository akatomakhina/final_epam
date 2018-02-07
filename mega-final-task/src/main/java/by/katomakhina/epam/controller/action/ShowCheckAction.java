package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.entity.User;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCheckAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ShowCheckAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, DAOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            if (request.getAttribute("id") != null) {
                id = (int) request.getAttribute("id");
            } else return ActionConstant.REDIRECT_TO_HOME;
        }
        Order order = ActionConstant.ORDER_SERVICE.getOrderById(id);
        if (isUser(request) && order.getIdUser() != (int) request.getSession(false).getAttribute("userId")) {
            return ActionConstant.REDIRECT_TO_HOME;
        }
        User user = ActionConstant.USER_SERVICE.getUserById(order.getIdUser());
        List<ProductItem> items = ActionConstant.ORDER_SERVICE.getItemsByOrder(order);
        request.setAttribute("user", user);
        request.setAttribute("order", order);
        request.setAttribute("items", items);
        request.getSession().setAttribute("orderID", null);
        Logger.info("check showed successfully");
        return new View("check");
    }
}
