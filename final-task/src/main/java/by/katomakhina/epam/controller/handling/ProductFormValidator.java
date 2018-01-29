package by.katomakhina.epam.controller.handling;

import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.product.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.List;

public class ProductFormValidator extends ValidatorImpl {
    public boolean execute(HttpServletRequest request, boolean isUpdate) throws ServiceException {
        ProductServiceImpl productService = new ProductServiceImpl();
        String name = request.getParameter("title");
        String price = request.getParameter("price");
        String quantity = request.getParameter("amount");
        List<String> messages = new ArrayList<>();
        if (name.equals("")) {
            messages.add("emptyForm");
        } else if (name.length() > STRING_LIMIT) {
            messages.add("invalidName");
        } else if (productService.isProductExist(name)&&!isUpdate) {
            messages.add("productIsExist");
        }
        if (isInteger(request, "price") && price.length() < STRING_LIMIT) {
            Integer priceInt = Integer.parseInt(price);
            if (priceInt <= 0) {
                messages.add("invalidPrice");
            }
        } else {
            messages.add("invalidPrice");
        }
        if (isInteger(request, "amount") && quantity.length() < STRING_LIMIT) {
            Integer quantityInt = Integer.parseInt(request.getParameter("amount"));
            if (quantityInt <= 0) {
                messages.add("invalidQuantity");
            }
        } else {
            messages.add("invalidQuantity");
        }
        if (!messages.isEmpty()){
            sendMessagesBySessionAttribute(messages, request);
        }

        return messages.isEmpty();
    }
}
