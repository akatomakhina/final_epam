package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.entity.User;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.user.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(LoginAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserServiceImpl userService = new UserServiceImpl();
        User user;
        View redirectToReferrer = new View(getReferrerName(request), ActionConstant.REDIRECT);
        boolean isValidPair = (userService.isEmailExist(email) && userService.isValidPair(email, password));
        if (isValidPair) {
            user = userService.getUserByEmail(email);
            request.getSession(false).setAttribute("loggedUser", user);
            request.getSession(false).setAttribute("userId", user.getId());
            request.getSession(false).setAttribute("loggedUserRole", user.getRole());
            Logger.info(user.getFirstName() + " " + user.getLastName() + " " + user.getLogin() + " " + "Logged successfully");

        } else {
            session.setAttribute("loginError", "Invalid Login or Password");
            Logger.info("Authorization is failed");
        }
        return redirectToReferrer;
    }
}
