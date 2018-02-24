package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(LogoutAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.setAttribute("userId", null);
        session.setAttribute("loggedUser", null);
        session.setAttribute("loggedUserRole", null);
        Logger.info("Logout successful");
        return ActionConstant.REDIRECT_TO_HOME;
    }
}
