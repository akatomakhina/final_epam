package by.katomakhina.epam.service.connection.impl;

import by.katomakhina.epam.dao.connection.impl.ConnectionDAOImpl;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.factory.impl.DAOFactoryImpl;
import by.katomakhina.epam.service.connection.ConnectionTestService;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionTestServiceImpl implements ConnectionTestService {

    private static final Logger Logger = LogManager.getLogger(ConnectionTestServiceImpl.class);

    @Override
    public boolean isConnected() throws ServiceException {
        try {
            DAOFactoryImpl factoryJdbc = new DAOFactoryImpl();
            ConnectionDAOImpl daoJdbc = factoryJdbc.getDAO(ConnectionDAOImpl.class);
            return daoJdbc.isConnected();
        } catch (DAOException e) {
            Logger.error("Cannot find out is connection with database exist", e);
            e.printStackTrace();
            throw new ServiceException("Cannot find out is connection with database exist");
        }
    }

    @Override
    public boolean isBasePopulated() throws ServiceException {
        try {
            DAOFactoryImpl factoryJdbc = new DAOFactoryImpl();
            ConnectionDAOImpl daoJdbc = factoryJdbc.getDAO(ConnectionDAOImpl.class);
            return daoJdbc.isBasePopulated();
        } catch (DAOException e) {
            Logger.error("Cannot find out is connection exist");
            e.printStackTrace();
            throw new ServiceException("Cannot find out is connection exist");
        }
    }

    @Override
    public void populateDatabase() throws ServiceException {
        try {
            DAOFactoryImpl factoryJdbc = new DAOFactoryImpl();
            ConnectionDAOImpl daoJdbc = factoryJdbc.getDAO(ConnectionDAOImpl.class);
            daoJdbc.populateDatabase();
        } catch (DAOException e) {
            Logger.error("Cannot populate database");
            e.printStackTrace();
            throw new ServiceException("Cannot populate database");
        }
    }
}
