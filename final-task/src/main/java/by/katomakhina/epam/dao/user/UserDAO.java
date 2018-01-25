package by.katomakhina.epam.dao.user;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.UserDAOException;
import by.katomakhina.epam.entity.User;

import java.util.List;

public interface UserDAO{
    public void createUser(User user) throws UserDAOException;
    public void deleteUser(User user) throws UserDAOException;
    public User findByEmail(String email) throws DAOException;
    public List<User> getAllUsers() throws UserDAOException;
    public void updateBalance(int balance, int userId) throws UserDAOException;
    public int getUserBalance(int userId) throws UserDAOException;
}
