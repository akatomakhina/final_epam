package by.katomakhina.epam.dao.catalog.impl;

import by.katomakhina.epam.dao.catalog.CatalogDAO;
import by.katomakhina.epam.dao.exception.CatalogDAOException;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import by.katomakhina.epam.entity.Catalog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogDAOImpl extends IdDAOImpl implements CatalogDAO {

    private static final Logger Logger = LogManager.getLogger(CatalogDAOImpl.class);

    public CatalogDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Catalog> getAllCatalogs() throws CatalogDAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("CATALOG_GET_ALL"));
            ResultSet resultSet = statement.executeQuery();
            List<Catalog> list = new ArrayList<>();
            while (resultSet.next()) {
                Catalog category = new Catalog();
                category.setId(resultSet.getInt("id_catalog"));
                category.setName(resultSet.getString("catalog_name"));
                list.add(category);
            }
            return list;
        } catch (SQLException e) {
            Logger.error("Cannot get all catalogs", e);
            e.printStackTrace();
            throw new CatalogDAOException("Cannot get all catalogs");
        }
    }
}
