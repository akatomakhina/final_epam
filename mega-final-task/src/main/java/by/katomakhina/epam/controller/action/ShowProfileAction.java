package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
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
        Integer idUser;
        if (isAuthorized(request) && param == null) {
            idUser = (int) request.getSession(false).getAttribute("userId");
        }

        if (!isAuthorized(request) && param == null) {
            Logger.info("User is not authorized");
            return ActionConstant.REDIRECT_TO_HOME;
        }
        if (isInteger(request, "id")) {
            idUser = Integer.parseInt(param);
        } else {
            Logger.info("Processing redirect to home-page");
            return ActionConstant.REDIRECT_TO_HOME;
        }
        User user = ActionConstant.USER_SERVICE.getUserById(idUser);
        request.setAttribute("user", user);
        request.setAttribute("firstName", user.getFirstName());
        System.out.println(user);
        return new View("profile");
    }
}
