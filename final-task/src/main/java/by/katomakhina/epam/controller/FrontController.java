package by.katomakhina.epam.controller;

import by.katomakhina.epam.controller.action.ActionFactory;
import by.katomakhina.epam.controller.action.ActionImpl;
import by.katomakhina.epam.controller.action.entity.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet", urlPatterns = "/do/*")
public class FrontController extends HttpServlet {

    private static final Logger Logger = LogManager.getLogger(FrontController.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ActionFactory factory = new ActionFactory();
        ActionImpl action = (ActionImpl) factory.getAction(req); //!!!!!!CAST!!!!!!!
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }
        processResult(action, req, resp);
    }

    private void processResult(ActionImpl action, HttpServletRequest req, HttpServletResponse resp) {
        View inf;
        boolean isRedirect;
        try {
            inf = action.execute(req, resp);
            Logger.info("requested page name is " + inf.getName());
            isRedirect = inf.isRedirect();
            if (isRedirect) {
                resp.sendRedirect(req.getContextPath() + "/do/" + inf.getName());
                Logger.info("redirect to {} is processed", req.getContextPath() + "/do/" + inf.getName());
            } else {
                String path = "/WEB-INF/jsp/" + inf.getName() + ".jsp";
                req.getRequestDispatcher(path).forward(req, resp);
                Logger.info("forward to {} is processed", path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error("Execution is failed", e);
            throw new RuntimeException("Execution is failed", e);

        }
    }

}
