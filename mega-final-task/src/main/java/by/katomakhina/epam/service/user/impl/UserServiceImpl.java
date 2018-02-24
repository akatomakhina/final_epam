package by.katomakhina.epam.service.user.impl;

import by.katomakhina.epam.controller.handling.PasswordHandler;
import by.katomakhina.epam.dao.abstractDAO.AbstractDAOFactory;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.ProductItemDAOException;
import by.katomakhina.epam.dao.factory.impl.DAOFactoryImpl;
import by.katomakhina.epam.dao.order.impl.OrderDAOImpl;
import by.katomakhina.epam.dao.product.impl.ProductItemDAOImpl;
import by.katomakhina.epam.dao.user.impl.UserDAOImpl;
import by.katomakhina.epam.entity.User;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger Logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public void deleteUser(int id) throws ServiceException, DAOException, ProductItemDAOException {
        DAOFactoryImpl factory = null;
        try {
            factory = new DAOFactoryImpl();
            factory.startTransaction();
            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            productItemDAO.deleteBasketByUser(id);
            productItemDAO.deleteOrderItemById(id);
            OrderDAOImpl orderDAO = factory.getDAO(OrderDAOImpl.class);
            orderDAO.deleteOrdersByUser(id);
            UserDAOImpl userDAO = factory.getDAO(UserDAOImpl.class);
            User user = new User();
            user.setId(id);
            userDAO.deleteUser(user);
            factory.commitTransaction();
        } catch (DAOException e) {
            try {
                factory.rollbackTransaction();
            } catch (DAOException e1) {
                Logger.error("Cannot rollback transaction");
                new ServiceException("Cannot rollback transaction");
            }
            Logger.error("Cannot delete user");
            e.printStackTrace();
            throw new ServiceException("Cannot delete user");
        } finally {
            factory.closeConnection();
        }
    }

    @Override
    public void registerUser(User user) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            PasswordHandler handler = new PasswordHandler();
            String code = handler.getHashedPassword(user.getPassword());
            user.setPassword(code);
            UserDAOImpl userDAO = factory.getDAO(UserDAOImpl.class);
            userDAO.createUser(user);
        } catch (DAOException e) {
            Logger.error("Cannot create user");
            e.printStackTrace();
            throw new ServiceException("Cannot create user");
        }
    }

    @Override
    public boolean isEmailExist(String email) throws ServiceException {
        try {
            boolean result = false;
            AbstractDAOFactory factory = new DAOFactoryImpl();
            UserDAOImpl userDAO = factory.getDAO(UserDAOImpl.class);
            User user = userDAO.findByEmail(email);
            if (user.getEmail() != null) {
                result = true;
            }
            return result;
        } catch (DAOException e) {
            Logger.error("Cannot check if email exist");
            e.printStackTrace();
            throw new ServiceException("Cannot check if email exist");
        }
    }

    @Override
    public boolean isValidPair(String email, String password) throws ServiceException {
        try {
            boolean isValid = false;
            AbstractDAOFactory factory = new DAOFactoryImpl();
            UserDAOImpl userDAO = factory.getDAO(UserDAOImpl.class);
            User user = userDAO.findByEmail(email);
            PasswordHandler handler = new PasswordHandler();
            String hashedPass = handler.getHashedPassword(password);
            String passFromBase = user.getPassword();
            if (hashedPass.equals(passFromBase)) {
                isValid = true;
            }
            return isValid;
        } catch (DAOException e) {
            Logger.error("Cannot check is pair valid", e);
            throw new ServiceException("Cannot check is pair valid");
        }
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            UserDAOImpl userDAO = factory.getDAO(UserDAOImpl.class);
            return userDAO.findByEmail(email);
        } catch (DAOException e) {
            Logger.error("Cannot get user by id", e);
            throw new ServiceException("Cannot get user by id");
        }
    }

    @Override
    public User getUserById(int id) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            UserDAOImpl userDAO = factory.getDAO(UserDAOImpl.class);
            return (User) userDAO.findById(id, User.class); //!!!!!!!!!!! Cast
        } catch (DAOException e) {
            Logger.error("Cannot get user by id");
            e.printStackTrace();
            throw new ServiceException("Cannot get user by id");
        }
    }

    @Override
    public List<User> getAllUser() throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            UserDAOImpl userDAO = factory.getDAO(UserDAOImpl.class);
            return userDAO.getAllUsers();
        } catch (DAOException e) {
            Logger.error("Cannot get all users");
            e.printStackTrace();
            throw new ServiceException("Cannot get all users");
        }
    }

    @Override
    public void updateBalance(double balance, int idUser) throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            UserDAOImpl userDAO = factory.getDAO(UserDAOImpl.class);
            int result = (int) (balance + userDAO.getUserBalance(idUser));
            userDAO.updateBalance(result, idUser);
        } catch (DAOException e) {
            Logger.error("cannot update balance");
            e.printStackTrace();
            throw new ServiceException("cannot update balance");
        }
    }

    @Override
    public double getUserBalance(int idUser) throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            UserDAOImpl userDAO = factory.getDAO(UserDAOImpl.class);
            return userDAO.getUserBalance(idUser);
        } catch (DAOException e) {
            Logger.error("Cannot get user balance");
            e.printStackTrace();
            throw new ServiceException("Cannot get user balance");
        }
    }
}
