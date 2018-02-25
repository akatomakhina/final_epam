package by.katomakhina.epam.dao.factory;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.ResultSet;
import java.sql.SQLException;


public class EntityFactory<T> {
    private static final Logger Logger = LogManager.getLogger(EntityFactory.class);


    public T getInstanceFromResultSet(ResultSet set, Class<T> aClass) throws DAOException {

        T object = null;
        try {
            object = aClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            Logger.error("Cannot create an instance of class", e);
            e.printStackTrace();
        }
        try {
            if (object instanceof User) {
                object = (T) getUser(set);
            } else if (object instanceof Catalog) {
                object = (T) getCatalog(set);
            } else if (object instanceof Product) {
                object = (T) getProduct(set);
            } else if (object instanceof Order) {
                object = (T) getOrder(set);
            } else if (object instanceof Subcatalog) {
                object = (T) getSubcatalog(set);
            }
        } catch (ClassCastException e) {
            throw new DAOException("Cannot get item by id");
        }
        return object;
    }

    private User getUser(ResultSet set) throws DAOException {
        try {
            User user = new User();
            while (set.next()) {
                user.setId(set.getInt("id_client"));
                user.setFirstName(set.getString("firstname"));
                user.setLastName(set.getString("lastname"));
                user.setLogin(set.getString("login"));
                user.setEmail(set.getString("email"));
                user.setRole(set.getString("role_name"));
                user.setPassword(set.getString("password"));
                user.setBanned(set.getBoolean("banned"));
                user.setBalance(set.getDouble("balance"));
            }
            return user;
        } catch (SQLException e) {
            Logger.error("Cannot get user by id");
            e.printStackTrace();
            throw new DAOException("Cannot get user by id");
        }
    }

    private Catalog getCatalog(ResultSet set) throws DAOException {
        try {
            Catalog catalog = new Catalog();
            while (set.next()) {
                catalog.setId(set.getInt("id_catalog"));
                catalog.setName(set.getString("catalog_name"));
            }
            return catalog;
        } catch (SQLException e) {
            Logger.error("Cannot get catalog by id");
            e.printStackTrace();
            throw new DAOException("Cannot get catalog by id");
        }
    }


    private Product getProduct(ResultSet set) throws DAOException {
        try {
            Product product = new Product();
            while (set.next()) {
                product.setId(set.getInt("id_product"));
                product.setTitle(set.getString("title"));
                product.setPrice(set.getDouble("price"));
                product.setDescription(set.getString("description"));
                product.setVendor(set.getString("vendor"));
                product.setId_catalog(set.getInt("id_catalog"));
            }
            return product;
        } catch (SQLException e) {
            Logger.error("Cannot get product by id");
            e.printStackTrace();
            throw new DAOException("Cannot get product instance");
        }
    }

    private Order getOrder(ResultSet set) throws DAOException {
        try {
            Order order = new Order();
            while (set.next()) {
                order.setId(set.getInt("id_order"));
                Logger.debug("order id_order = {}", order.getId());
                order.setDate(set.getTimestamp("date"));
                order.setIdUser(set.getInt("id_client"));
                order.setAmount(set.getInt("amount"));
                Status status = new Status();
                status.setName(set.getString("status"));
                order.setStatus(status);
            }
            return order;
        } catch (SQLException e) {
            Logger.error("Cannot get order by id");
            e.printStackTrace();
            throw new DAOException("Cannot get order instance");
        }
    }

    private Subcatalog getSubcatalog(ResultSet set) throws DAOException {
        try {
            Subcatalog subcatalog = new Subcatalog();
            while (set.next()) {
                subcatalog.setId(set.getInt("id_catalog"));
                subcatalog.setName(set.getString("catalog_name"));
            }
            return subcatalog;
        } catch (SQLException e) {
            Logger.error("Cannot get subcatalog by id");
            e.printStackTrace();
            throw new DAOException("Cannot get subcatalog instance");
        }
    }
}
