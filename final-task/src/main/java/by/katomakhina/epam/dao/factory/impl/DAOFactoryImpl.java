package by.katomakhina.epam.dao.factory.impl;

import by.katomakhina.epam.connection.ConnectionPoolException;
import by.katomakhina.epam.connection.DBConnectionPool;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.factory.DAOFactory;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import by.katomakhina.epam.dao.order.impl.OrderDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactoryImpl implements DAOFactory {

    private static final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final Logger Logger = LogManager.getLogger(DAOFactoryImpl.class);
    private Connection connection;


    public DAOFactoryImpl() throws DAOException {
        try {
            if (connection == null) {

                connection = connectionPool.getConnection();

            }
        } catch (ConnectionPoolException e) {
            Logger.error("Cannot create connection", e);
            e.printStackTrace();
            throw new DAOException("Cannot initialize connection", e);
        }
    }

    @Override
    public <T> T getDAO(Class<T> aClass) throws DAOException {
        IdDAOImpl dao;
        try {
            Constructor<T> constructor = aClass.getConstructor(Connection.class);
            Logger.info("Connection is null - {}", connection == null);
            dao = (IdDAOImpl) constructor.newInstance(connection);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new DAOException("Cannot get dao instance", e);
        }
        return (T) dao;
    }

    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException e) {
            Logger.error("Cannot start transaction", e);
            e.printStackTrace();
        }
    }

    public void commitTransaction() throws DAOException {
        try {
            connection.commit();
            connectionPool.closeConnection();
        } catch (SQLException | ConnectionPoolException e) {
            Logger.error("Cannot commit transaction", e);
            e.printStackTrace();
            throw new DAOException("Cannot commit transaction");
        }
    }

    public void rollbackTransaction() throws DAOException {
        try {
            connection.rollback();
            connectionPool.closeConnection();
        } catch (SQLException | ConnectionPoolException e) {
            Logger.error("Cannot rollback connection", e);
            throw new DAOException("cannot rollback transaction");
        }
    }

    public void closeConnection() {
        try {
            connectionPool.closeConnection();
        } catch (ConnectionPoolException connectionPoolException) {
            Logger.error("cannot close connection");
            connectionPoolException.printStackTrace();
            throw new RuntimeException("Cannot close connection");
        }
    }
}
