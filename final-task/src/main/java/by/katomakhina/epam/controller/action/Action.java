package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.entity.ProductItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface Action {
    public boolean isAdmin(HttpServletRequest request);
    public boolean isUser(HttpServletRequest request);
    public boolean isAuthorized(HttpServletRequest request);
    public String getReferrerName(HttpServletRequest request);
    public boolean isInteger(HttpServletRequest request, String key);
    public void buySuccessTemplate(HttpServletRequest request, List<ProductItem> itemList, int userId);
}
