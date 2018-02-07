package by.katomakhina.epam.controller.action;

import by.katomakhina.epam.controller.entity.View;
import by.katomakhina.epam.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageAction extends ActionImpl {
    private final View view;

    public ShowPageAction(String page) {
        view = new View(page);
    }

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return view;
    }
}
