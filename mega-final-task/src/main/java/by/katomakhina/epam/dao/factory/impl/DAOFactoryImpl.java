package by.katomakhina.epam.dao.factory.impl;

import by.katomakhina.epam.connection.ConnectionPoolException;
import by.katomakhina.epam.connection.DBConnectionPool;
import by.katomakhina.epam.dao.abstractDAO.AbstractDAOFactory;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

/*
 * <code>DAOFactoryImpl</code> создает
 * фабрику, обеспечивающую соединение с бд с использованием пула соединений.
 * Поддержка транзакций.
 */

public class DAOFactoryImpl extends AbstractDAOFactory{

    private static final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final Logger Logger = LogManager.getLogger(DAOFactoryImpl.class);
    private Connection connection;


    /*
     * конструктор <code>DAOFactoryImpl</code> создает
     * соединение.
     */
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


    /**
     * Создает сущность дао для соединения с бд.
     * Использует коннект для соединения с бд.
     *
     * @param clazz accepts
     * @param <T>
     * @return
     * @throws DAOException
     */
    @Override
    public <T> T getDAO(Class<T> clazz) throws DAOException {
        IdDAOImpl dao;
        try {
            Constructor<T> constructor = clazz.getConstructor(Connection.class);
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
            throw new DAOException("Cannot rollback transaction");
        }
    }

    public void closeConnection() throws DAOException {
        try {
            connectionPool.closeConnection();
        } catch (ConnectionPoolException connectionPoolException) {
            Logger.error("Cannot close connection");
            connectionPoolException.printStackTrace();
            throw new DAOException("Cannot close connection");
        }
    }
}
