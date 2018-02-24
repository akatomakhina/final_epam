package by.katomakhina.epam.controller.handling;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface Validator {
    public String getRegex(String key);
    public boolean isStringMatches(String regex, String string);
    public boolean isInteger(HttpServletRequest request, String key);
    public boolean isDouble(HttpServletRequest request, String key);
    public void sendMessagesByRequestAttribute(List<String> messages, HttpServletRequest request);
    public void sendMessagesBySessionAttribute(List<String> messages, HttpServletRequest request);
}
