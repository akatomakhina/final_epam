package by.katomakhina.epam.controller.handling;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ValidatorImpl implements Validator {

    public static final int STRING_LIMIT = 30;

    @Override
    public String getRegex(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("regex");
        return bundle.getString(key);
    }

    @Override
    public boolean isStringMatches(String regex, String string) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
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
    public void sendMessagesByRequestAttribute(List<String> messages, HttpServletRequest request) {
        for (String key : messages) {
            request.setAttribute(key, "msg");
        }
    }

    @Override
    public void sendMessagesBySessionAttribute(List<String> messages, HttpServletRequest request) {
        for (String key : messages) {
            request.getSession().setAttribute(key, "msg");
        }
    }
}
