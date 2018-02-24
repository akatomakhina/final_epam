package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.entity.Basket;
import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BuyBasketAction extends ActionImpl{

    private static final Logger Logger = LogManager.getLogger(BuyBasketAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Integer idUser = (int) request.getSession().getAttribute("userId");
        List<ProductItem> productItemList = ActionConstant.PRODUCT_SERVICE.getBasketByUserId(idUser);
        Basket basket = ActionConstant.PRODUCT_SERVICE.createBasket(productItemList);
        View view;
        if (basket.isValid()) {
            buySuccessTemplate(request, productItemList, idUser);
            view = new View("payment");
        } else {
            request.getSession().setAttribute("invalidCart", "you should check amount in your basket");
            Logger.info("cannot buy users basket because of invalid amount");
            view = new View("cart", ActionConstant.REDIRECT);
        }
        return view;

    }
}
