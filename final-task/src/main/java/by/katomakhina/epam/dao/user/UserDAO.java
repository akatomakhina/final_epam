package by.katomakhina.epam.dao.user;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.User;

public interface UserDAO{
    public void create(User user) throws DAOException;
}
