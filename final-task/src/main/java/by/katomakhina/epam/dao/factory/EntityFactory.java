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
            while (set.next()) {  //iteration
                user.setId(set.getInt("id"));
                user.setFirstName(set.getString("first_name"));
                user.setLastName(set.getString("last_name"));
                user.setLogin(set.getString("login"));
                user.setPassword(set.getString("password"));
                user.setEmail(set.getString("e-mail"));
                user.setBanned(set.getBoolean("banned"));
                user.setRole(set.getString("role_name"));
                user.setBalance(set.getInt("balance"));
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
                catalog.setId(set.getInt("id"));
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
                product.setId(set.getInt("id"));
                product.setTitle(set.getString("title"));
                product.setDescription(set.getString("description"));
                product.setPrice(set.getInt("price"));
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
                order.setId(set.getInt("id"));
                //Logger.debug("order id = {}", order.getId());
                order.setAmount(set.getInt("amount"));
                order.setDate(set.getTimestamp("order_date"));
                Status status = new Status();
                status.setName(set.getString("status"));
                order.setStatus(status);
                order.setIdUser(set.getInt("id_user"));
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
                subcatalog.setId(set.getInt("id"));
                subcatalog.setName(set.getString("subcatalog_name"));
            }
            return subcatalog;
        } catch (SQLException e) {
            Logger.error("Cannot get subcategory by id");
            e.printStackTrace();
            throw new DAOException("Cannot get subcategory instance");
        }
    }
}
