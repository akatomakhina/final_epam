package by.katomakhina.epam.dao.factory;

import by.katomakhina.epam.dao.exception.DAOException;

public interface DAOFactory {
    public <T> T getDAO(Class<T> aClass) throws DAOException;
}
