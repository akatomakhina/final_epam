package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.controller.handling.PaginationHandler;
import by.katomakhina.epam.entity.User;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.user.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAdminPageAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(ShowAdminPageAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View view;
        if (isAdmin(request)) {
            UserServiceImpl service = new UserServiceImpl();
            PaginationHandler<User> paginationHandler = new PaginationHandler<>(service.getAllUser(), ActionConstant.PAGE_VOLUME);
            int pageNumber = paginationHandler.getPageNumber(request);
            List<User> fragment = paginationHandler.getPageContent(pageNumber);
            List<Integer> pagesList = paginationHandler.getPageNumbers();
            request.setAttribute("usersList", fragment);
            if (fragment.isEmpty()) {
                request.setAttribute("emptyMessage", "Empty list");
                Logger.info("User list is empty");
            }

            request.setAttribute("pagesList", pagesList);
            view = new View("admin-page");
        } else {
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
