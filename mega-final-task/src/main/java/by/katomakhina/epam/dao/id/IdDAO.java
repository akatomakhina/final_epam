package by.katomakhina.epam.dao.id;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.Id;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IdDAO<T extends Id> {
    public T findById(int id, Class<T> aClass) throws DAOException;
    public int generatedKeyGetter(PreparedStatement statement) throws DAOException;
    public void closeStatement(PreparedStatement statement);
    //public void closeResultSet(ResultSet resultSet);
    public String getQuery(String key);
}
