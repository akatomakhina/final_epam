package by.katomakhina.epam.dao.user;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.User;

import java.util.List;

public interface UserDAO{
    public void createUser(User user) throws DAOException;
    public void deleteUser(User user) throws DAOException;
    public User findByEmail(String email) throws DAOException;
    public List<User> getAllUsers() throws DAOException;
    public void updateBalance(int balance, int userId) throws DAOException;
    public int getUserBalance(int userId) throws DAOException;
}
