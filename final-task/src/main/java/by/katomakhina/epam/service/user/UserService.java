package by.katomakhina.epam.service.user;

import by.katomakhina.epam.entity.User;
import by.katomakhina.epam.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    public void deleteUser(int id) throws ServiceException;
    public void registerUser(User user) throws ServiceException;
    public boolean isEmailExist(String email) throws ServiceException;
    public boolean isValidPair(String email, String password) throws ServiceException;
    public User getUserByEmail(String email) throws ServiceException;
    public User getUserById(int id) throws ServiceException;
    public List<User> getAllUser() throws ServiceException;
    public void updateBalance(int balance, int idUser) throws ServiceException;
    public int getUserBalance(int idUser) throws ServiceException;
}
