package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLocaleAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ChangeLocaleAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String language = request.getParameter("lang");
        Logger.info(request.getLocale().toString());
        if (language.equals("ru") || language.equals("en")) {
            Cookie locale = new Cookie("locale", language);
            response.addCookie(locale);
            Logger.info("locale in cookie is " + locale.getValue());
        }
        return new View(getReferrerName(request), ActionConstant.REDIRECT);
    }
}
