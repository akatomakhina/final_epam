package by.katomakhina.epam.dao.factory;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.ResultSet;
import java.sql.SQLException;


public class EntityFactory<T> {
    private static final Logger LOGGER = LogManager.getLogger(EntityFactory.class);


    public T getInstanceFromResSet(ResultSet set, Class<T> aClass) throws DAOException {

        T object = null;
        try {
            object = aClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            LOGGER.error("Cannot create an instance of Class", e);
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
                object = (T) getSubcategory(set);
            }
        } catch (ClassCastException e) {
            throw new DAOException("Cannot get item by Id");
        }
        return object;
    }

    private User getUser(ResultSet set) throws DAOException {
        try {
            User user = new User();
            while (set.next()) {
                user.setId(set.getInt("id"));
                user.setFirstName(set.getString("FirstName"));
                user.setLastName(set.getString("LastName"));
                user.setLogin(set.getString("loggin"));
                user.setPassword(set.getString("password"));
                user.setEmail(set.getString("email"));
                user.setBalance(set.getInt("balance"));
                user.setBanned(set.getBoolean("banned"));
                //user.setRole(set.getString("role_name"));
            }
            return user;
        } catch (SQLException e) {
            LOGGER.error("Cannot get User by Id");
            e.printStackTrace();
            throw new DAOException("Cannot get User by Id");
        }
    }

    private Catalog getCatalog(ResultSet set) throws DAOException {
        try {
            Catalog category = new Catalog();
            while (set.next()) {
                category.setName(set.getString("category_name"));
                category.setId(set.getInt("id"));
            }
            return category;
        } catch (SQLException e) {
            LOGGER.error("Cannot get Category by Id");
            e.printStackTrace();
            throw new DAOException("Cannot get Category by id");
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
            LOGGER.error("Cannot get product by Id");
            e.printStackTrace();
            throw new DAOException("Cannot get product instance");
        }
    }

    private Order getOrder(ResultSet set) throws DAOException {
        try {
            Order order = new Order();
            while (set.next()) {
                order.setId(set.getInt("id"));
                LOGGER.debug("order id = {}", order.getId());
                order.setDate(set.getTimestamp("order_date"));
                order.setAmount(set.getInt("amount"));
                Status status = new Status();
                status.setName(set.getString("status"));
                order.setStatus(status);
                order.setIdUser(set.getInt("id_user"));
            }
            return order;
        } catch (SQLException e) {
            LOGGER.error("Cannot get Order by Id");
            e.printStackTrace();
            throw new DAOException("Cannot get Order instance");
        }
    }

    private Subcatalog getSubcategory(ResultSet set) throws DAOException {
        try {
            Subcatalog category = new Subcatalog();
            while (set.next()) {
                category.setName(set.getString("category_name"));
                category.setId(set.getInt("id"));
            }
            return category;
        } catch (SQLException e) {
            LOGGER.error("Cannot get Subcategory by Id");
            e.printStackTrace();
            throw new DAOException("Cannot get Subcategory instance");
        }
    }
}
