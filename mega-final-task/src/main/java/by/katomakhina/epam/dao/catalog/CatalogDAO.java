package by.katomakhina.epam.dao.catalog;

import by.katomakhina.epam.dao.exception.CatalogDAOException;
import by.katomakhina.epam.entity.Catalog;

import java.util.List;

public interface CatalogDAO {
    public List<Catalog> getAllCategories() throws CatalogDAOException;
}
