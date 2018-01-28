package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.service.catalog.impl.CatalogServiceImpl;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.order.impl.OrderServiceImpl;
import by.katomakhina.epam.service.product.impl.ProductServiceImpl;
import by.katomakhina.epam.service.user.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public abstract class ActionImpl implements Action {

    private static final Logger Logger = LogManager.getLogger(Action.class);

    public static final UserServiceImpl USER_SERVICE = new UserServiceImpl();
    public static final ProductServiceImpl PRODUCT_SERVICE = new ProductServiceImpl();
    public static final OrderServiceImpl ORDER_SERVICE = new OrderServiceImpl();
    public static final CatalogServiceImpl CATEGORY_SERVICE = new CatalogServiceImpl();

    public static final boolean REDIRECT = true;
    public static final View REDIRECT_TO_HOME = new View("home-page", REDIRECT);

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
        String name = request.getHeader("referer").substring(25);
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
    public void buySuccessTemplate(HttpServletRequest request, List<ProductItem> itemList, int userId) {
        request.getSession().setAttribute("itemList", itemList);
        request.getSession().setAttribute("productReference", getReferrerName(request));
        int totalAmount = 0;
        try {
            totalAmount = ORDER_SERVICE.getTotalAmount(itemList);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("orderTotalAmount", totalAmount);
        int userBalance = 0;
        try {
            userBalance = USER_SERVICE.getUserBalance(userId);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("userBalance", userBalance);
        if (userBalance >= totalAmount) {
            request.getSession().setAttribute("allowBalance", "msg");
        }
    }
}
