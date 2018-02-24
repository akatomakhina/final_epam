package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.ProductItemDAOException;
import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public abstract class ActionImpl implements Action {

    private static final Logger Logger = LogManager.getLogger(by.katomakhina.epam.controller.action.ActionImpl.class);

    public abstract View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, DAOException, ProductItemDAOException;

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedUserRole") != null) {
            return session.getAttribute("loggedUserRole").equals("Admin");
        } else return false;
    }

    @Override
    public boolean isUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("loggedUserRole") != null) {
            return session != null && session.getAttribute("loggedUserRole").equals("User");
        } else return false;
    }

    @Override
    public boolean isAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("userId") != null;
    }

    @Override
    public String getReferrerName(HttpServletRequest request) {
        String name = request.getHeader("referrer").substring(25);
        name = name.replace(".jsp", "");
        Logger.info("referrer name: " + name);
        return name;
    }

    @Override
    public boolean isInteger(HttpServletRequest request, String key) {
        try {
            Integer.parseInt(request.getParameter(key));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public void buySuccessTemplate(HttpServletRequest request, List<ProductItem> productItemList, int idUser) {
        request.getSession().setAttribute("itemList", productItemList);
        request.getSession().setAttribute("productReference", getReferrerName(request));
        double totalAmount = 0;
        totalAmount = ActionConstant.ORDER_SERVICE.getTotalAmount(productItemList);
        request.getSession().setAttribute("orderTotalAmount", totalAmount);
        double userBalance = 0;
        try {
            userBalance =  ActionConstant.USER_SERVICE.getUserBalance(idUser);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("userBalance", userBalance);
        if (userBalance >= totalAmount) {
            request.getSession().setAttribute("allowBalance", "msg");
        }
    }
}

