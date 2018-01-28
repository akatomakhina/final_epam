package by.katomakhina.epam.dao.connection;

import by.katomakhina.epam.dao.exception.DAOException;

public interface ConnectionDAO {
    public boolean isBasePopulated();
    public void populateDatabase() throws DAOException;
}
