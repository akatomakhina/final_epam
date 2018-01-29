package by.katomakhina.epam.controller.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static Map<String, Action> actions;
    private static final Logger Logger = LogManager.getLogger(ActionFactory.class);

    public ActionFactory() {
        actions = new HashMap<>();
        actions.put("POST/register", new RegisterAction());
        actions.put("GET/register", new ShowPageAction("register"));
        actions.put("GET/login-page", new ShowLoginPage());
        actions.put("GET/products", new ShowCatalogAction());
        actions.put("GET/product-page", new ShowProductAction());
        actions.put("POST/buy-product", new BuyProductAction());
        actions.put("POST/add-product", new AddProductAction());
        actions.put("POST/login", new LoginAction());
        actions.put("GET/basket", new ShowBasketAction());
        actions.put("GET/profile", new ShowProfileAction());
        actions.put("GET/admin-page", new ShowAdminPageAction());
        actions.put("GET/delete-user", new DeleteUserAction());
        actions.put("GET/remove-from-cart", new RemoveFromBasketAction());
        actions.put("GET/buy-basket", new BuyBasketAction());
        actions.put("GET/example", new ShowPageAction("example"));
        actions.put("GET/home-page", new ShowPageAction("home-page"));
        actions.put("GET/payment", new ShowPaymentPage());
        actions.put("POST/update-balance", new UpdateBalanceAction());
        actions.put("POST/payment", new PaymentAction());
        actions.put("GET/logout", new LogoutAction());
        actions.put("POST/update-product", new UpdateProductAction());
        actions.put("POST/create-product", new NewProductAction());
        actions.put("GET/locale", new ChangeLocaleAction());
        actions.put("GET/catalog-manager", new ShowCatalogManagerAction());
        actions.put("GET/order-manager", new ShowOrderManager());
        actions.put("GET/my-orders", new ShowProfileOrdersAction());
        actions.put("GET/check", new ShowCheckAction());
        actions.put("POST/change-status", new SetStatusAction());
        actions.put("GET/change-order", new ChangeSortOrderAction());
        actions.put("POST/delete-product", new DeleteFromCatalogAction());
        actions.put("POST/update-basket", new EditBasketAction());
    }

    public Action getAction(HttpServletRequest request) {

        Logger.info("request key is " + request.getMethod() + request.getPathInfo());

        return actions.get(request.getMethod() + request.getPathInfo());
    }

}
