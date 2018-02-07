package by.katomakhina.epam.controller.handling;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class PaymentValidator extends ValidatorImpl {
    public boolean execute(HttpServletRequest request) {
        String cardNumber = request.getParameter("cc-number");
        String cvc = request.getParameter("cc-cvc");
        List<String> messages = new ArrayList<>();
        if (!isStringMatches(getRegex("notEmpty"), cardNumber)) {
            messages.add("cardNumEmpty");
        }
        if (!isStringMatches(getRegex("notEmpty"), cvc)) {
            messages.add("cvcEmpty");
        }
        if (!isStringMatches(getRegex("card"), cardNumber) || cardNumber.length() > STRING_LIMIT) {
            messages.add("invalidCard");
        }
        if (!isStringMatches(getRegex("cvc"), cvc) || cvc.length() > STRING_LIMIT) {
            messages.add("invalidCVC");
        }
        if (!messages.isEmpty()){
            sendMessagesByRequestAttribute(messages, request);
        }

        return messages.isEmpty();

    }
}
