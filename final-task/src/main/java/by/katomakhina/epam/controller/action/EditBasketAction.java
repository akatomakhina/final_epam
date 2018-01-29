package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.action.constant.ActionConstant;
import by.katomakhina.epam.controller.action.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditBasketAction extends ActionImpl {

    private static final Logger Logger = LogManager.getLogger(EditBasketAction.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        View view;
        View redirectToCart = new View("basket", ActionConstant.REDIRECT);
        if (isUser(request)){
            try {
                Integer cartItemId = Integer.parseInt(request.getParameter("basketItemId"));
                Integer productId = Integer.parseInt(request.getParameter("idProduct"));
                Integer quantity = Integer.parseInt(request.getParameter("amount"));
                if (!ActionConstant.PRODUCT_SERVICE.isAmountValid(quantity, productId)){
                    throw new NumberFormatException();
                }
                if (!ActionConstant.PRODUCT_SERVICE.updateBasketItem(cartItemId, quantity)){
                    request.getSession().setAttribute("redundantUpdate", "msg");
                }else {
                    request.getSession().setAttribute("successUpdate", "msg");
                }
                view = redirectToCart;
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
                request.getSession().setAttribute("basketItemUpdateFail", "msg");
                Logger.error("invalid data input", e);
                view = redirectToCart;
            }
        }else{
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
