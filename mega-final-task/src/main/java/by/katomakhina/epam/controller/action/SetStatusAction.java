package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetStatusAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(SetStatusAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View view;
        if (isAdmin(request)) {
            String status = request.getParameter("status");
            Integer idOrder = Integer.parseInt(request.getParameter("order"));
            ActionConstant.ORDER_SERVICE.setOrderStatus(idOrder, status);
            request.getSession().setAttribute("successUpdateMsg", "Status change successful");
            Logger.info("Status change successful");
            view = new View("order-manager", ActionConstant.REDIRECT);
        } else {
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
