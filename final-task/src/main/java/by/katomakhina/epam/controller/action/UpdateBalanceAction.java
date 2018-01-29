package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateBalanceAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(UpdateBalanceAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View view;
        View redirectToReferrer = new View(getReferrerName(request), ActionConstant.REDIRECT);
        if (isUser(request)) {
            if (isInteger(request, "balance")) {
                int balance = Integer.parseInt(request.getParameter("balance"));
                int idUser = (int) request.getSession().getAttribute("idUser");
                if (balance > 0 && balance < 1000) {
                    ActionConstant.USER_SERVICE.updateBalance(balance, idUser);
                    view = redirectToReferrer;
                    request.getSession().setAttribute("success", "msg");
                } else {
                    Logger.info("invalid balance");
                    request.getSession().setAttribute("invalidUpdate", "msg");
                    view = redirectToReferrer;
                }
            } else {
                Logger.info("invalid balance");
                request.getSession().setAttribute("invalidUpdate", "msg");
                view = redirectToReferrer;
            }
        } else {
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
