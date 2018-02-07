package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.controller.handling.PaginationHandler;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowProfileOrdersAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ShowProfileOrdersAction.class);

    private static final int PAGE_VOLUME = 10;

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Integer userId;
        if (session != null && session.getAttribute("idUser") != null) {
            userId = (int) session.getAttribute("idUser");
        } else {
            return new View("home-page", true);
        }
        List<Order> orderList = ActionConstant.ORDER_SERVICE.getAllUserOrders(userId);
        if (!orderList.isEmpty()) {
            PaginationHandler<Order> paginationHandler = new PaginationHandler<>(orderList, PAGE_VOLUME);
            int pageNumber = paginationHandler.getPageNumber(request);
            List<Order> fragment = paginationHandler.getPageContent(pageNumber);
            Logger.debug("order date = {}; totalAmount = {}", fragment.get(0).getDate(), fragment.get(0).getAmount());
            List<Integer> pagesList = paginationHandler.getPageNumbers();
            request.setAttribute("orderList", fragment);
            request.setAttribute("pagesList", pagesList);
            Logger.info("profile orders showed successfully");
        } else {
            Logger.info("profile orders list is empty");
            request.setAttribute("emptyMsg", "empty");
        }
        return new View("my-orders");
    }
}
