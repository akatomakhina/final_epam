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
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String amount = request.getParameter("quantity");
        List<String> messages = new ArrayList<>();
        if (name.equals("")) {
            messages.add("emptyForm");
        } else if (name.length() > STRING_LIMIT) {
            messages.add("invalidName");
        } else if (productService.isProductExist(name)&&!isUpdate) {
            messages.add("productIsExist");
        }
        if (isDouble(request, "price") && price.length() < STRING_LIMIT) {
            Double parseDouble = Double.parseDouble(price);
            if (parseDouble <= 0) {
                messages.add("invalidPrice");
            }
        } else {
            messages.add("invalidPrice");
        }
        if (isInteger(request, "quantity") && amount.length() < STRING_LIMIT) {
            Integer amountInt = Integer.parseInt(request.getParameter("amount"));
            if (amountInt <= 0) {
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
