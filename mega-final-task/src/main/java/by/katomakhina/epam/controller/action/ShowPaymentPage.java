package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPaymentPage extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ShowPaymentPage.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View view;
        if (isAuthorized(request) && (request.getSession().getAttribute("itemList") != null)) {
            view = new View("payment", ActionConstant.REDIRECT);
        } else {
            Logger.info("attempt to obtain unauthorized access");
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
