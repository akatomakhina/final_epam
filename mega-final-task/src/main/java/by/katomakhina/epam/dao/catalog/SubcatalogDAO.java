package by.katomakhina.epam.dao.catalog;

import by.katomakhina.epam.dao.exception.CatalogDAOException;
import by.katomakhina.epam.entity.Subcatalog;

import java.util.List;

public interface SubcatalogDAO {
    public List<Subcatalog> getGetSubcategoriesByParent(int parentId) throws CatalogDAOException;
}
