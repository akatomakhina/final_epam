package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.entity.User;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowProfileAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ShowProfileAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String param = request.getParameter("id");
        Integer id;
        if (isAuthorized(request) && param == null) {
            id = (int) request.getSession(false).getAttribute("idUser");
        }

        if (!isAuthorized(request) && param == null) {
            Logger.info("user is not authorized");
            return ActionConstant.REDIRECT_TO_HOME;
        }
        if (isInteger(request, "id")) {
            id = Integer.parseInt(param);
        } else {
            Logger.info("processing redirect to home-page");
            return ActionConstant.REDIRECT_TO_HOME;
        }
        User user = ActionConstant.USER_SERVICE.getUserById(id);
        request.setAttribute("user", user);
        return new View("profile");
    }
}
