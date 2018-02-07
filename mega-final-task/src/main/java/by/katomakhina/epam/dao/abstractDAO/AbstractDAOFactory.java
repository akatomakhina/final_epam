package by.katomakhina.epam.dao.abstractDAO;

import by.katomakhina.epam.dao.exception.DAOException;

public abstract class AbstractDAOFactory {
    public abstract <T> T getDAO(Class<T> clazz) throws DAOException;
}
