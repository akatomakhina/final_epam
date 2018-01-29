package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeSortOrderAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ChangeSortOrderAction.class);


    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String sortOrder = request.getParameter("sort-order");
        if (sortOrder.equals("byName") || sortOrder.equals("byPrice")) {
            request.getSession().setAttribute("sortOrder", sortOrder);
            Logger.info("sort order in this session is" + sortOrder);
        }
        return new View(getReferrerName(request), ActionConstant.REDIRECT);
    }
}
