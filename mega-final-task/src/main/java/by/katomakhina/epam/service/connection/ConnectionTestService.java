package by.katomakhina.epam.service.connection;

import by.katomakhina.epam.service.exception.ServiceException;

public interface ConnectionTestService {
    public boolean isConnected() throws ServiceException;
    public boolean isBasePopulated() throws ServiceException;
    public void populateDatabase() throws ServiceException;
}
