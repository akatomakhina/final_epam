package by.katomakhina.epam.dao.product.impl;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.ProductDAOException;
import by.katomakhina.epam.dao.factory.EntityFactory;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import by.katomakhina.epam.dao.product.ProductDAO;
import by.katomakhina.epam.entity.Product;
import com.mysql.jdbc.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl extends IdDAOImpl implements ProductDAO {

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

    private static final Logger Logger = LogManager.getLogger(ProductDAOImpl.class);

    @Override
    public List<Product> getAllProducts() throws DAOException { //!!!!!!!!!!!!
        List<Product> products = new ArrayList<>();
        String query = getQuery("PRODUCTS_GET_ALL");
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id_product"));
                product.setTitle(resultSet.getString("title"));
                product.setDescription(resultSet.getString("description"));
                product.setVendor(resultSet.getString("vendor"));
                product.setPrice(resultSet.getInt("price"));
                product.setId_catalog(resultSet.getInt("id_catalog"));
                products.add(product);
            }
        } catch (SQLException e) {
            Logger.error("Cannot get all product");
            e.printStackTrace();
            throw new DAOException("Cannot get all product");
        }
        return products;
    }

    @Override
    public int createProduct(Product product) throws DAOException {
        try {
            String query = getQuery("CREATE_PRODUCT");
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getTitle());
            statement.setString(2, product.getDescription());
            statement.setString(3, product.getVendor());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getId_catalog());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("No rows affected");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            return generatedKeyGetter(statement);
        } catch (SQLException e) {
            Logger.error("Cannot create product in database");
            e.printStackTrace();
            throw new DAOException("Cannot create product in database");
        }
    }

    @Override
    public Product findProductByName(String name) throws DAOException {
        try {
            String query = getQuery("PRODUCT_GET_BY_NAME");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            EntityFactory<Product> factory = new EntityFactory<>();
            return factory.getInstanceFromResultSet(resultSet, Product.class);
        } catch (SQLException e) {
            Logger.error("Cannot find product by name");
            e.printStackTrace();
            throw new DAOException("Cannot find product by name");
        }
    }

    @Override
    public void deleteProduct(int id) throws ProductDAOException {
        try {
            String query = getQuery("DELETE_PRODUCT");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                Logger.error("Delete failure, no rows affected");
                throw new SQLException("Delete failure, no rows affected");
            }
        } catch (SQLException e) {
            Logger.error("Cannot delete Product by name");
            e.printStackTrace();
            throw new ProductDAOException("Cannot delete Product by name");
        }
    }

    @Override
    public int updateProduct(Product product) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("UPDATE_PRODUCT"));
            product.getTitle();
            statement.setString(1, product.getTitle());
            statement.setString(2, product.getDescription());
            statement.setString(3, product.getVendor());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getId_catalog());
            statement.setInt(6, product.getId());
            int affectedRows = statement.executeUpdate();
            statement.close();
            return affectedRows;

        } catch (SQLException e) {
            Logger.error("Cannot update product");
            e.printStackTrace();
            throw new DAOException("Cannot update product");
        }
    }

    @Override
    public List<Product> getSubcatalogProduct(int id) throws ProductDAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("GET_CATALOG_PRODUCTS"));
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Product> list = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id_product"));
                product.setTitle(resultSet.getString("title"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getInt("price"));
                product.setId_catalog(resultSet.getInt("id_catalog"));
                list.add(product);
            }

            return list;

        } catch (SQLException e) {
            Logger.error("Cannot get Product by subcatalog id");
            e.printStackTrace();
            throw new ProductDAOException("Cannot get Product by subcatalog id");
        }
    }
}
