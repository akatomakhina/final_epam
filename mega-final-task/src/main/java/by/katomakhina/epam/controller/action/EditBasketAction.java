package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.constant.ActionConstant;
import by.katomakhina.epam.controller.entity.View;
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
        View redirectToBasket = new View("basket", ActionConstant.REDIRECT);
        if (isUser(request)){
            try {
                Integer basketItemId = Integer.parseInt(request.getParameter("cartItemId"));
                Integer idProduct = Integer.parseInt(request.getParameter("productId"));
                Integer amount = Integer.parseInt(request.getParameter("quantity"));
                if (!ActionConstant.PRODUCT_SERVICE.isAmountValid(amount, idProduct)){
                    throw new NumberFormatException();
                }
                if (!ActionConstant.PRODUCT_SERVICE.updateBasketItem(basketItemId, amount)){
                    request.getSession().setAttribute("redundantUpdate", "msg");
                }else {
                    request.getSession().setAttribute("successUpdate", "msg");
                }
                view = redirectToBasket;
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
                request.getSession().setAttribute("cartItemUpdateFail", "msg");
                Logger.error("invalid data input", e);
                view = redirectToBasket;
            }
        }else{
            view = ActionConstant.REDIRECT_TO_HOME;
        }
        return view;
    }
}
