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

    private final static Logger logger = LogManager.getLogger(DBConnectionPool.class);

    private static final ResourceBundle Bundle = ResourceBundle.getBundle("database");
    private static final String URL = Bundle.getString("URL");
    private static final String USERNAME = Bundle.getString("USERNAME");
    private static final String PASSWORD = Bundle.getString("PASSWORD");
    private static final BlockingQueue<Connection> CONNECTION_QUEUE = new ArrayBlockingQueue<>(Integer.parseInt(Bundle.getString("CONNECTION_LIMIT")));

    private Server server;
    private Connection connection;

    private DBConnectionPool() {
    }


    public Connection getConnection() throws ConnectionPoolException {
        try {
            //if (isDbConnected(connection))
            if (!CONNECTION_QUEUE.isEmpty()) {
                while (!CONNECTION_QUEUE.isEmpty()) {
                    connection = CONNECTION_QUEUE.peek();
                    if (connection.isValid(500)) {
                        return connection;
                    }
                }
            } else {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                if (isFreeConnection(connection)) {
                    CONNECTION_QUEUE.add(connection);
                }
                return getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionPoolException("Cannot get connection");
        }
        return connection;
    }

    public boolean isFreeConnection(Connection connection) throws ConnectionPoolException {
        try {
            if (!connection.isClosed()) {
                return true;
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Cannot add free connection");
        }
        return false;
    }

    public void closeConnection() throws ConnectionPoolException {
        try {
            if (CONNECTION_QUEUE.peek() != null) {
                CONNECTION_QUEUE.poll().close();
                logger.info("Connection closed and removed from queue");
            }
        } catch (SQLException e) {
            logger.error("Cannot close connection");
            throw new ConnectionPoolException("Cannot close connection");
        }
    }

    public void startServer() throws ConnectionPoolException {
        try {
            Driver.load();
            server = Server.createTcpServer();
            server.start();
        } catch (Exception e) {
            logger.error("Cannot register driver", e);
            e.printStackTrace();
            throw new ConnectionPoolException("Cannot initialize driver");
        }
    }

    public void stopServer() {
        try {
            closeConnection();
        } catch (ConnectionPoolException connectionPoolException) {
            logger.error("Cannot close connection");
            connectionPoolException.printStackTrace();
        }
        server.stop();
    }

    private static class PoolInstanceMaster {
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
        return PoolInstanceMaster.INSTANCE;
    }
}
