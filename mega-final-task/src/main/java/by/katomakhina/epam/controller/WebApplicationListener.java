package by.katomakhina.epam.controller;

import by.katomakhina.epam.connection.ConnectionPoolException;
import by.katomakhina.epam.connection.DBConnectionPool;
import by.katomakhina.epam.controller.action.EditBasketAction;
import by.katomakhina.epam.service.connection.ConnectionTestService;
import by.katomakhina.epam.service.connection.impl.ConnectionTestServiceImpl;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class WebApplicationListener implements ServletContextListener {
    private static final Logger Logger = LogManager.getLogger(WebApplicationListener.class);
    private static final DBConnectionPool POOL = DBConnectionPool.getInstance();

    public void contextInitialized(ServletContextEvent sce) {
        try {
            POOL.startServer();
            ConnectionTestServiceImpl service = new ConnectionTestServiceImpl();
            if (service.isConnected()&&!service.isBasePopulated()){
                service.populateDatabase();
                Logger.info("database filled successfully");
            } else {
                Logger.info("filling the database is not required");
            }
        } catch (ConnectionPoolException connectionPoolException) {
            Logger.error("Cannot connect to database");
            connectionPoolException.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        Logger.info("Connection pool init");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        POOL.stopServer();
        Logger.info("Connection pool shutdown");
    }
}
