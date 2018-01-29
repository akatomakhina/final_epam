package by.katomakhina.epam.controller.action.constant;

import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.service.catalog.impl.CatalogServiceImpl;
import by.katomakhina.epam.service.order.impl.OrderServiceImpl;
import by.katomakhina.epam.service.product.impl.ProductServiceImpl;
import by.katomakhina.epam.service.user.impl.UserServiceImpl;

public final class ActionConstant {
    public static final UserServiceImpl USER_SERVICE = new UserServiceImpl();
    public static final ProductServiceImpl PRODUCT_SERVICE = new ProductServiceImpl();
    public static final OrderServiceImpl ORDER_SERVICE = new OrderServiceImpl();
    public static final CatalogServiceImpl CATEGORY_SERVICE = new CatalogServiceImpl();

    public static final boolean REDIRECT = true;
    public static final View REDIRECT_TO_HOME = new View("home-page", REDIRECT);

    public static final boolean PAY_BY_BALANCE = true;
    public static final boolean PAY_BY_CARD = false;

    public static final int PAGE_VOLUME = 10;
}


