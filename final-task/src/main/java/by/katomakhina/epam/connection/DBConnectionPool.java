package by.katomakhina.epam.connection;

import java.sql.Connection;

import org.h2.Driver;
import org.h2.tools.Server;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBConnectionPool {
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("database");
    private final static Logger LOGGER = LogManager.getLogger(DBConnectionPool.class);
    private static final String URL = BUNDLE.getString("URL");
    private static final String USERNAME = BUNDLE.getString("USERNAME");
    private static final String PASSWORD = BUNDLE.getString("PASSWORD");
    private static final BlockingQueue<Connection> QUEUE = new ArrayBlockingQueue<>(Integer.parseInt(BUNDLE.getString("CONNECTION_LIMIT")));
    private Server server;
    private Connection connection;

    private DBConnectionPool() {
    }

    public void init() throws ConnectionPoolException {
        try {
            Driver.load();
            server = Server.createTcpServer();
            server.start();
        } catch (Exception e) {
            LOGGER.error("Cannot register driver", e);
            e.printStackTrace();
            throw new ConnectionPoolException("Cannot initialize driver");
        }
    }

    public synchronized Connection getConnection() throws ConnectionPoolException {
        try {
            //if (isDbConnected(connection))
            if (!QUEUE.isEmpty()) {
                while (!QUEUE.isEmpty()) {
                    connection = QUEUE.peek();
                    if (connection.isValid(500)) {
                        return connection;
                    }

                }
            } else {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                freeConnection(connection);
                return getConnection();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionPoolException("Cannot get connection");
        }
        return connection;
    }

    public synchronized void freeConnection(Connection connection) throws ConnectionPoolException {
        try {
            if (!connection.isClosed()) {
                QUEUE.add(connection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("cannot add free connection");
        }
    }

    public void closeConnection() throws ConnectionPoolException {
        try {
            if (QUEUE.peek() != null) {
                QUEUE.poll().close();
                LOGGER.info("Connection closed and removed from queue");
            }
        } catch (SQLException e) {
            LOGGER.error("cannot close connection");
            throw new ConnectionPoolException("cannot close connection");
        }
    }

    public void shutdown() {
        try {
            closeConnection();
        } catch (ConnectionPoolException connectionPoolException) {
            LOGGER.error("cannot close connection");
            connectionPoolException.printStackTrace();
        }
        server.stop();
    }

    private static class PoolInstanceHolder {
        private static final DBConnectionPool INSTANCE = new DBConnectionPool();

    }

    /*public boolean isDbConnected(Connection connection) {
        //final String CHECK_SQL_QUERY = "SELECT 1";
        try {
            if (!connection.isClosed() || connection != null) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }*/


    public static DBConnectionPool getInstance() {
        return PoolInstanceHolder.INSTANCE;
    }
}
