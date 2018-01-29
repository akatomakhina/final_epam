package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.entity.Catalog;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCatalogManagerAction extends ActionImpl {
    private static final Logger Logger = LogManager.getLogger(ShowCatalogManagerAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View view;
        if (isAdmin(request)) {
            List<Catalog> categories = ActionConstant.CATEGORY_SERVICE.getCatalogTree();
            request.setAttribute("categories", categories);
            Logger.info("admin went to catalog-manager page successful");
            view = new View("catalog-manager");
        } else {
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
