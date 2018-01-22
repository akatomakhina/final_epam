package by.katomakhina.epam.dao.id.impl;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.factory.EntityFactory;
import by.katomakhina.epam.dao.id.IdDAO;
import by.katomakhina.epam.entity.Id;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public abstract class IdDAOImpl implements IdDAO {
    private static final Logger Logger = LogManager.getLogger(EntityFactory.class);

    public Connection connection;

    @Override
    public Id findById(int id, Class aClass) throws DAOException {
        Id object;
        try {
            ResourceBundle resource = ResourceBundle.getBundle("resource");
            String key = aClass.getSimpleName().toUpperCase() + "_GET_BY_ID";
            String query = resource.getString(key);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            EntityFactory<Id> factory = new EntityFactory<>();
            object = factory.getInstanceFromResultSet(set, aClass);
        } catch (Exception e) {
            Logger.error("Cannot find by ID");
            e.printStackTrace();
            throw new DAOException("Cannot find by ID");
        }
        return object;
    }

    @Override
    public int generatedKeyGetter(PreparedStatement statement) throws DAOException {
        try {
            ResultSet keys = statement.getGeneratedKeys();
            int idToReturn = 0;
            if (keys.next()) {
                idToReturn = keys.getInt(1);
            }
            if (idToReturn == 0) {
                throw new SQLException("Cannot take generated id");
            }
            return idToReturn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Cannot take generated keys");
        }
    }

    @Override
    public void closeStatement(PreparedStatement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            Logger.error("Cannot close statement", e);
            e.printStackTrace();
        } catch (NullPointerException e) {
            Logger.error("Connection is null");
        }
    }

    @Override
    public void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            Logger.error("Cannot close result set", e);
            e.printStackTrace();
        } catch (NullPointerException e) {
            Logger.error("Connection is null");
        }
    }

    @Override
    public String getQuery(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("resource");
        return bundle.getString(key);
    }
}
