package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.ProductItemDAOException;
import by.katomakhina.epam.entity.User;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.user.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(DeleteUserAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ProductItemDAOException, DAOException {
        UserServiceImpl service = new UserServiceImpl();
        int id = Integer.parseInt(request.getParameter("id"));
        View redirectToAdminPage = new View("admin-page", ActionConstant.REDIRECT);
        User user = service.getUserById(id);
        if (user.getRole().equals("User")) {
            service.deleteUser(id);
            request.getSession().setAttribute("successDelete", "User delete successful");
            Logger.info("Username: " + user.getFirstName() + "User delete successful");
        } else {
            request.getSession().setAttribute("failDelete", "Cannot delete admin");
        }
        return redirectToAdminPage;
    }
}
