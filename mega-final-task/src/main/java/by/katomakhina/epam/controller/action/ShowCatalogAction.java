package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.controller.handling.CatalogHandler;
import by.katomakhina.epam.entity.Catalog;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCatalogAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ShowCatalogAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        CatalogHandler handler = new CatalogHandler();
        handler.getRequestedList(request);
        List<Catalog> catalog = ActionConstant.CATEGORY_SERVICE.getCatalogTree();
        request.setAttribute("catalog", catalog);
        Logger.info("catalog showed successfully");
        return new View("products");
    }
}
