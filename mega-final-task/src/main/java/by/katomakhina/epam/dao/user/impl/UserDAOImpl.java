package by.katomakhina.epam.dao.user.impl;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.UserDAOException;
import by.katomakhina.epam.dao.factory.EntityFactory;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import by.katomakhina.epam.dao.user.UserDAO;
import by.katomakhina.epam.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserDAOImpl extends IdDAOImpl implements UserDAO {

    private static final Logger Logger = LogManager.getLogger(UserDAOImpl.class);

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUser(User user) throws UserDAOException {
        ResourceBundle resource = ResourceBundle.getBundle("inquery");
        String query = resource.getString("CREATE_USER");
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getEmail());
            statement.setInt(5, 2);
            statement.setString(6, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.error("Cannot create user in DAO", e);
            e.printStackTrace();
            throw new UserDAOException("Cannot create user");

        }
    }

    @Override
    public void deleteUser(User user) throws UserDAOException {
        try {
            ResourceBundle resource = ResourceBundle.getBundle("inquery");
            String query = resource.getString("DELETE_USER");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.execute();
        } catch (SQLException e) {
            Logger.error("DAO delete operation is failed", e);
            e.printStackTrace();
            throw new UserDAOException("DAO delete operation is failed");
        }
    }

    @Override
    public User findByEmail(String email) throws DAOException {
        User user;
        try {
            ResourceBundle resource = ResourceBundle.getBundle("inquery");
            String query = resource.getString("USER_GET_BY_EMAIL");
            EntityFactory<User> factory = new EntityFactory<>();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            user = factory.getInstanceFromResultSet(set, User.class);
        } catch (SQLException e) {
            Logger.error("DAO find by email operation is failed", e);
            e.printStackTrace();
            throw new DAOException("DAO find by email operation is failed");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws UserDAOException {
        List<User> users = new ArrayList<>();
        String query = getQuery("USER_GET_ALL");
        try {

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id_client"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setLogin(resultSet.getString("login"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("name"));
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            Logger.error("Cannot get all users", e);
            e.printStackTrace();
            throw new UserDAOException("Cannot get all users");
        }
    }

    @Override
    public void updateBalance(int balance, int userId) throws UserDAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("UPDATE_USER_BALANCE"));
            statement.setInt(1, balance);
            statement.setInt(2, userId);
            int redone = statement.executeUpdate();
            if (redone == 0) {
                Logger.error("No rows affected");
                throw new SQLException();
            }
        } catch (SQLException e) {
            Logger.error("Cannot update balance");
            e.printStackTrace();
            throw new UserDAOException("Cannot update balance");
        }
    }

    @Override
    public double getUserBalance(int userId) throws UserDAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("GET_USER_BALANCE"));
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            double balance = 0;
            while (resultSet.next()) {
                balance = resultSet.getDouble("balance");
            }
            resultSet.close();
            return balance;
        } catch (SQLException e) {
            Logger.error("Cannot get user balance");
            e.printStackTrace();
            throw new UserDAOException("Cannot get user balance");
        }
    }
}
