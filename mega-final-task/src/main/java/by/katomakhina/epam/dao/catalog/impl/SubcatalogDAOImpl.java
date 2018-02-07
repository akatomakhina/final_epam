package by.katomakhina.epam.dao.catalog.impl;

import by.katomakhina.epam.dao.catalog.SubcatalogDAO;
import by.katomakhina.epam.dao.exception.CatalogDAOException;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import by.katomakhina.epam.entity.Subcatalog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubcatalogDAOImpl extends IdDAOImpl implements SubcatalogDAO {

    private static final Logger Logger = LogManager.getLogger(SubcatalogDAOImpl.class);

    public SubcatalogDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Subcatalog> getGetSubcategoriesByParent(int parentId) throws CatalogDAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("SUBCATALOG_GET_BY_PARENT"));
            statement.setInt(1, parentId);
            ResultSet resultSet = statement.executeQuery();
            List<Subcatalog> subcategories = new ArrayList<>();
            while (resultSet.next()) {
                Subcatalog category = new Subcatalog();
                category.setId(resultSet.getInt("id_catalog"));
                category.setName(resultSet.getString("catalog_name"));
                subcategories.add(category);
            }
            return subcategories;
        } catch (SQLException e) {
            Logger.error("Cannot get subcatalog by parent ID", e);
            e.printStackTrace();
            throw new CatalogDAOException("Cannot get subcatalog by parent ID");
        }
    }
}
