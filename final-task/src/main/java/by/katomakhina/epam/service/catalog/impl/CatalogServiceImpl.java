package by.katomakhina.epam.service.catalog.impl;

import by.katomakhina.epam.dao.abstractDAO.AbstractDAOFactory;
import by.katomakhina.epam.dao.catalog.impl.CatalogDAOImpl;
import by.katomakhina.epam.dao.catalog.impl.SubcatalogDAOImpl;
import by.katomakhina.epam.dao.exception.CatalogDAOException;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.factory.impl.DAOFactoryImpl;
import by.katomakhina.epam.entity.Catalog;
import by.katomakhina.epam.entity.Subcatalog;
import by.katomakhina.epam.service.catalog.CatalogService;
import by.katomakhina.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CatalogServiceImpl implements CatalogService {
    private static final Logger Logger = LogManager.getLogger(CatalogServiceImpl.class);

    @Override
    public List<Catalog> getCatalogTree() throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            CatalogDAOImpl catalogDAO = factory.getDAO(CatalogDAOImpl.class);
            SubcatalogDAOImpl subCategoryDAO = factory.getDAO(SubcatalogDAOImpl.class);
            List<Catalog> catalogs = catalogDAO.getAllCategories();
            for (Catalog catalog : catalogs) {
                catalog.setSubCatalog(subCategoryDAO.getGetSubcategoriesByParent(catalog.getId()));
            }
            return catalogs;
        } catch (DAOException e) {
            Logger.error("Cannot get catalog tree", e);
            e.printStackTrace();
            throw new ServiceException("Cannot get catalog tree");
        } catch (CatalogDAOException e) {
            Logger.error("Cannot get catalog tree", e);
            e.printStackTrace();
            throw new ServiceException("Cannot get catalog tree");
        }
    }

    @Override
    public Subcatalog getSubcatalogById(int id) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            SubcatalogDAOImpl subCatalogDAO = factory.getDAO(SubcatalogDAOImpl.class);
            return (Subcatalog) subCatalogDAO.findById(id, Subcatalog.class); //!!!!!!!!CAST
        } catch (DAOException e) {
            Logger.error("Cannot ges subcategory by id");
            e.printStackTrace();
            throw new ServiceException("Cannot ges subcategory by id");
        }
    }
}
