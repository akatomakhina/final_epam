package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.controller.handling.PaginationHandler;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.entity.Status;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowOrderManager extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ShowOrderManager.class);

    private static final int PAGE_VOLUME = 10;

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View view;
        if (isAdmin(request)) {
            List<Order> orders = ActionConstant.ORDER_SERVICE.getAllOrders();
            if (!orders.isEmpty()) {
                PaginationHandler<Order> paginationHandler = new PaginationHandler<>(orders, PAGE_VOLUME);
                int pageNumber = paginationHandler.getPageNumber(request);
                List<Order> fragment = paginationHandler.getPageContent(pageNumber);
                List<Integer> pagesList = paginationHandler.getPageNumbers();
                List<Status> statusList = ActionConstant.ORDER_SERVICE.getStatuses();
                request.setAttribute("statusList", statusList);
                request.setAttribute("orderList", fragment);
                request.setAttribute("pagesList", pagesList);
                request.setAttribute("successUpdate", "msg");
                Logger.info("order-manager showed successfully");
            }else {
                request.setAttribute("emptyMessage", "msg");
            }
            view = new View("order-manager");
        } else {
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
