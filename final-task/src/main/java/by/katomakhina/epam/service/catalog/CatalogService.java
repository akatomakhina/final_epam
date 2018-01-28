package by.katomakhina.epam.service.catalog;

import by.katomakhina.epam.entity.Catalog;
import by.katomakhina.epam.entity.Subcatalog;
import by.katomakhina.epam.service.exception.ServiceException;

import java.util.List;

public interface CatalogService {
    public List<Catalog> getCatalogTree() throws ServiceException;
    public Subcatalog getSubcatalogById(int id) throws ServiceException;
}
