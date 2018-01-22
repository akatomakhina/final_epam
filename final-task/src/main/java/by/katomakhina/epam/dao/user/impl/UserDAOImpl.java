package by.katomakhina.epam.dao.user.impl;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.factory.EntityFactory;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import by.katomakhina.epam.dao.user.UserDAO;
import by.katomakhina.epam.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserDAOImpl extends IdDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(EntityFactory.class);

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User user) throws DAOException {
        ResourceBundle resource = ResourceBundle.getBundle("resource");
        String query = resource.getString("CREATE_USER");
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(4, user.getLogin());
            statement.setString(4, user.getEmail());
            statement.setInt(5, 2);
            statement.setString(6, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot create user in DAO", e);
            e.printStackTrace();
            throw new DAOException("Cannot create user");

        }
    }
}
