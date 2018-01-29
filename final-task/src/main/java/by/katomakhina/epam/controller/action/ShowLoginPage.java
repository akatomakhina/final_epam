package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowLoginPage extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ShowLoginPage.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        request.setAttribute("lock", "Lock login form in sidebar");
        Logger.info("login-page showed successfully");
        return new View("login-page");
    }
}
