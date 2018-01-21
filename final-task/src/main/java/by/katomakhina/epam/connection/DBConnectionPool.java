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

/**
 * Connection pool holds created connections, because creating a network connection to a database server is expensive.
 * Пул соединений содержит созданные соединения, потому что создание сетевого подключения к серверу базы данных дорого.
 */

public class DBConnectionPool {
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("database");
    private final static Logger LOGGER = LogManager.getLogger(DBConnectionPool.class);
    private static final String URL = BUNDLE.getString("URL");
    private static final String USERNAME = BUNDLE.getString("USERNAME");
    private static final String PASSWORD = BUNDLE.getString("PASSWORD");
    private static final BlockingQueue<Connection> QUEUE = new ArrayBlockingQueue<>(Integer.parseInt(BUNDLE.getString("CONNECTION_LIMIT")));
    private Server server;
    private Connection connection;

    /**
     * prohibits creating instance of class outside the package
     * запрещает создание экземпляра класса вне пакета
     */
    private DBConnectionPool() {
    }

    /**
     * starts tcp server and loads H2 driver
     * In this case tcp server is more preferred than embedded mode because server enables using database editor when application is running.
     *
     * запускает tcp-сервер и загружает драйвер H2
     * В этом случае tcp-сервер более предпочтителен, чем встроенный режим, потому
     * что сервер позволяет использовать редактор базы данных при запуске приложения.
     *
     * @throws ConnectionPoolException
     */
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

    /**
     * returns an existing connection if it's not closed.
     * If connection is closed method gets connection by driver manager, and puts it to queue.
     * Then method invokes itself.
     *
     * возвращает существующее соединение, если оно не закрыто.
     * Если соединение закрыто, метод получает соединение диспетчера драйверов и помещает его в очередь.
     * Затем метод вызывает себя.
     *
     * @return
     * @throws ConnectionPoolException
     */
    public synchronized Connection getConnection() throws ConnectionPoolException {
        try {
            if (isDbConnected(connection)) {
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

    /**
     * closes connection and removes from queue
     *
     * закрывает соединение и удаляет из очереди
     *
     * @throws ConnectionPoolException
     */
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

    public  boolean isDbConnected(Connection connection) {
        //final String CHECK_SQL_QUERY = "SELECT 1";
        try {
            if(!connection.isClosed() || connection!=null){
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }


    public static DBConnectionPool getInstance() {
        return PoolInstanceHolder.INSTANCE;
    }
}
