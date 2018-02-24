package by.katomakhina.epam.controller.handling;


import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.user.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RegisterValidator extends ValidatorImpl {
    public boolean execute(HttpServletRequest request) throws ServiceException {
        UserServiceImpl service = new UserServiceImpl();
        String firstName = request.getParameter("first name").trim();
        String lastName = request.getParameter("last name").trim();
        String login = request.getParameter("login").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm password");
        List<String> messages = new ArrayList<>();
        if (!isStringMatches(getRegex("notEmpty"), firstName)) {
            messages.add("firstNameEmpty");
        } else if (!isStringMatches(getRegex("name"), firstName) || firstName.length() > STRING_LIMIT) {
            messages.add("invalidFirstName");
        }
        if (!isStringMatches(getRegex("notEmpty"), lastName)) {
            messages.add("lastNameEmpty");
        } else if (!isStringMatches(getRegex("name"), lastName) || lastName.length() > STRING_LIMIT) {
            messages.add("invalidLastName");
        }
        if (!isStringMatches(getRegex("notEmpty"), login)) {
            messages.add("loginEmpty");
        } else if (!isStringMatches(getRegex("name"), login) || login.length() > STRING_LIMIT) {
            messages.add("invalidLogin");
        }
        if (!isStringMatches(getRegex("notEmpty"), email)) {
            messages.add("emailEmpty");
        } else if (!isStringMatches(getRegex("email"), email) || email.length() > STRING_LIMIT) {
            messages.add("invalidEmail");
        } else if (service.isEmailExist(email)) {
            messages.add("busyEmail");
        }
        if (!isStringMatches(getRegex("notEmpty"), password)) {
            messages.add("passwordIsEmpty");
        } else if (!isStringMatches(getRegex("password"), password) || password.length() > STRING_LIMIT) {
            messages.add("invalidPassword");
        } else if (!password.equals(confirmPassword)) {
            messages.add("confirmError");
        }
        if (!messages.isEmpty()) {
            sendMessagesByRequestAttribute(messages, request);
        }
        return messages.isEmpty();
    }
}
