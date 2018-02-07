package by.katomakhina.epam.dao.connection.impl;

import by.katomakhina.epam.dao.connection.ConnectionDAO;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDAOImpl extends IdDAOImpl implements ConnectionDAO{

    private static final Logger Logger = LogManager.getLogger(ConnectionDAOImpl.class);

    public ConnectionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    @Override
    public boolean isBasePopulated() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("SELECT * FROM role");
            return true;
        } catch (SQLException e) {
            Logger.warn("Cannot find out is base populated");
            return false;
        }
    }

    @Override
    public void populateDatabase() throws DAOException {
        try {
            InputStream inputStream = ConnectionDAOImpl.class.getResourceAsStream("/shop.sql");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            RunScript.execute(connection, reader);
        } catch (SQLException e) {
            Logger.error("Cannot populate database", e);
            e.printStackTrace();
            throw new DAOException("Cannot populate database");
        }
    }
}
