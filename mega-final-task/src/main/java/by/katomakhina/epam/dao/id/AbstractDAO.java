package by.katomakhina.epam.dao.id;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.factory.EntityFactory;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import by.katomakhina.epam.entity.Id;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public abstract class AbstractDAO <T extends Id> {
    private static final Logger Logger = LogManager.getLogger(AbstractDAO.class);
    Connection connection;

    public T findById(int id, Class<T> tClass) throws DAOException {
        T object;
        try {
            ResourceBundle resource = ResourceBundle.getBundle("crud");
            String key = tClass.getSimpleName().toUpperCase() + "_GET_BY_ID";
            String query = resource.getString(key);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            EntityFactory<T> factory = new EntityFactory<>();
            object = factory.getInstanceFromResultSet(set, tClass);
        } catch (Exception e) {
            Logger.error("Cannot find by ID");
            e.printStackTrace();
            throw new DAOException("Cannot find by ID");
        }
        return object;
    }

    public int generatedKeyGetter(PreparedStatement statement) throws DAOException {
        try {
            ResultSet keys = statement.getGeneratedKeys();
            int idToReturn = 0;
            if (keys.next()) {
                idToReturn = keys.getInt(1);
            }
            if (idToReturn == 0) {
                throw new SQLException("Cannot obtain generated id");
            }
            return idToReturn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Cannot obtain generated keys");
        }
    }

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

    public String getQuery(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("crud");
        return bundle.getString(key);
    }


}

