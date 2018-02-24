package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.controller.handling.RegisterValidator;
import by.katomakhina.epam.entity.User;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.user.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterAction extends ActionImpl{

    private static final Logger Logger = LogManager.getLogger(RegisterAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        if (!isAuthorized(request)) {
            View inf = new View("register");
            RegisterValidator validator = new RegisterValidator();
            UserServiceImpl service = new UserServiceImpl();
            String firstName = request.getParameter("first name");
            String lastName = request.getParameter("last name");
            String login = request.getParameter("login");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (validator.execute(request)) {
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setLogin(login);
                user.setEmail(email);
                user.setPassword(password);
                service.registerUser(user);
                String message = "Congratulations! Registration successful, you can autorize now";
                request.getSession().setAttribute("message", message);
                inf.setName("login-page");
                Logger.info("Registration successfully");
                inf.setRedirect(true);
            }

            return inf;
        } else {
            Logger.info("Registration failure");
            return new View(getReferrerName(request), ActionConstant.REDIRECT);
        }
    }
}
