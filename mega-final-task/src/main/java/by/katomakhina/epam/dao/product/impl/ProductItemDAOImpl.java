package by.katomakhina.epam.dao.product.impl;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.ProductItemDAOException;
import by.katomakhina.epam.dao.factory.impl.DAOFactoryImpl;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import by.katomakhina.epam.dao.product.ProductItemDAO;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.entity.Product;
import by.katomakhina.epam.entity.ProductItem;
import com.mysql.jdbc.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductItemDAOImpl extends IdDAOImpl implements ProductItemDAO {

    private static final Logger Logger = LogManager.getLogger(ProductItemDAOImpl.class);

    public ProductItemDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ProductItem> getAllBasketByUserId(int idUser) throws DAOException {
        try {
            List<ProductItem> itemList = new ArrayList<>();
            DAOFactoryImpl factoryJdbc = new DAOFactoryImpl();
            ProductDAOImpl ProductDAO = factoryJdbc.getDAO(ProductDAOImpl.class);
            String query = getQuery("GET_BASKET_BY_USER");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idUser);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductItem item = new ProductItem();
                item.setId(resultSet.getInt("id_basket"));

                int idProduct = resultSet.getInt("id_product");
                item.setProduct((Product) ProductDAO.findById(idProduct, Product.class)); //!!!!!!!!!!!!(кастование)

                item.setAmount(resultSet.getInt("amount"));
                itemList.add(item);
            }
            return itemList;
        } catch (SQLException | DAOException e) {
            Logger.error("Cannot get all basket items by id", e);
            e.printStackTrace();
            throw new DAOException("Cannot get all basket items by id");
        }
    }

    @Override
    public void removeFromBasketById(int id) throws ProductItemDAOException {
        PreparedStatement statement = null;
        try {
            String query = getQuery("DELETE_FROM_BASKET");
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                Logger.warn("Cannot remove from basket");
                throw new ProductItemDAOException("Cannot remove from basket");
            }
        } catch (SQLException e) {
            Logger.error("Cannot remove from basket");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot remove from basket");

        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public void addToBasket(ProductItem item, int idUser) throws ProductItemDAOException {
        try {
            String query = getQuery("ADD_PRODUCT_TO_BASKET");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idUser);
            statement.setInt(2, item.getProduct().getId());
            statement.setInt(3, item.getAmount());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                Logger.error("No rows affected");
                throw new SQLException("No rows affected");
            }
        } catch (SQLException e) {
            Logger.error("Cannot add product to basket");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot add product to basket");
        }
    }

    @Override
    public int findAmountByProductWarehouse(int idProduct) throws DAOException {
        try {
            ResourceBundle resource = ResourceBundle.getBundle("inquiry");
            String query = resource.getString("ITEM_GET_QUANTITY");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idProduct);
            ResultSet resultSet = statement.executeQuery();
            int amount = 0;
            while (resultSet.next()) {
                amount = resultSet.getInt("amount");
            }
            return amount;
        } catch (SQLException e) {
            Logger.error("Cannot find amount by product");
            e.printStackTrace();
            throw new DAOException("Cannot find amount by product");
        }
    }

    @Override
    public int updateAmountInWarehouse(int amount, int idProduct) throws DAOException {
        try {
            String query = getQuery("UPDATE_WAREHOUSE_QUANTITY");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, amount);
            statement.setInt(2, idProduct);
            int affectedRows = statement.executeUpdate();
            return affectedRows;
        } catch (SQLException e) {
            Logger.error("Cannot update product item in warehouse", e);
            e.printStackTrace();
            throw new DAOException("Cannot update product item in warehouse");
        }
    }

    @Override
    public void createOrderItem(ProductItem ProductItem, int idOrder) throws DAOException {
        try {
            String query = getQuery("CREATE_ITEM");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ProductItem.getProduct().getId());
            statement.setInt(2, idOrder);
            statement.setInt(3, ProductItem.getAmount());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            Logger.error("Cannot add order item to database", e);
            e.printStackTrace();
            throw new DAOException("Cannot add order item to database");
        }
    }

    /*@Override
    public void deleteFromWarehouse(int productId) throws ProductItemDAOException {
        try {
            String query = getQuery("DELETE_FROM_WAREHOUSE_BY_PRODUCT");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                Logger.warn("DELETE FROM WAREHOUSE operation failed, no rows affected.");
                throw new SQLException("DELETE FROM WAREHOUSE operation failed, no rows affected.");
            }
        } catch (SQLException e) {
            Logger.error("Cannot delete from warehouse", e);
            throw new ProductItemDAOException("Cannot delete from warehouse");
        }
    }*/


    @Override
    public void deleteFromWarehouseByProduct(int idProduct) throws ProductItemDAOException {
        try {
            String query = getQuery("DELETE_FROM_WAREHOUSE_BY_PRODUCT");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idProduct);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.error("Cannot delete from warehouse");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot delete from warehouse");
        }
    }

    @Override
    public void deleteBasketByUser(int idUser) throws DAOException {
        try {
            String query = getQuery("DELETE_USER_CART");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idUser);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.error("Cannot delete from warehouse");
            e.printStackTrace();
            throw new DAOException("Cannot delete from warehouse");
        }
    }

    @Override
    public void deleteOrderItemById(int idUser) throws ProductItemDAOException {
        try {
            String query = getQuery("DELETE_USER_ITEMS");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idUser);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.error("Cannot delete orderItems");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot delete orderItems");
        }
    }

    @Override
    public void updateAmountInBasket(int amount, int idProduct) throws ProductItemDAOException {
        try {
            String query = getQuery("UPDATE_BASKET_AMOUNT");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, amount);
            statement.setInt(2, idProduct);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding to basket, no rows affected.");
            }
        } catch (SQLException e) {
            Logger.error("Cannot update basket item in warehouse");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot update basket item in warehouse");
        }
    }

    @Override
    public int getBasketAmount(int idProduct) throws ProductItemDAOException {
        try {
            String query = getQuery("BASKET_ITEM_AMOUNT");
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, idProduct);
            ResultSet resultSet = statement.executeQuery();
            int amount = 0;
            while (resultSet.next()) {
                amount = resultSet.getInt("amount");
            }
            return amount;
        } catch (SQLException e) {
            Logger.error("Cannot find amount by product");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot find amount by product");
        }
    }



    @Override
    public void createWarehouseEntry(int idProduct, int amount) throws ProductItemDAOException {
        try {
            String query = getQuery("CREATE_WAREHOUSE_ENTRY");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idProduct);
            statement.setInt(2, amount);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failure while creating warehouse entry, no rows affected");
            }

        } catch (SQLException e) {
            Logger.error("Cannot delete from warehouse");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot delete from warehouse");
        }
    }

    @Override
    public void deleteFromBasketByProduct(int idProduct) throws ProductItemDAOException {
        try {
            String query = getQuery("DELETE_FROM_BASKET_BY_PRODUCT");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idProduct);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.error("Cannot delete from basket");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot delete from basket");
        }
    }

    @Override
    public void deleteOrderItemsByProduct(int idProduct) throws ProductItemDAOException {
        try {
            String query = getQuery("DELETE_ORDER_ITEMS_BY_PRODUCT");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idProduct);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.error("Cannot delete from warehouse");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot delete from warehouse");
        }
    }

    @Override
    public List<ProductItem> getItemsByOrder(Order order) throws ProductItemDAOException { //!!!!!!
        try {
            List<ProductItem> list = new ArrayList<>();
            String query = getQuery("ITEM_GET_BY_ORDER_ID");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, order.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductItem item = new ProductItem();
                Product product = new Product();
                product.setId(resultSet.getInt("id_product"));
                product.setTitle(resultSet.getString("title"));
                product.setPrice(resultSet.getInt("price"));
                item.setProduct(product);
                item.setAmount(resultSet.getInt("amount"));
                list.add(item);
            }
            return list;
        } catch (SQLException e) {
            Logger.error("Cannot get item by order id", e);
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot get item by order id");
        }
    }

    @Override
    public boolean updateBasketItemById(Integer basketItemId, Integer amount) throws ProductItemDAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("UPDATE_BASKET_ITEM_BY_ID"));
            statement.setInt(1, amount);
            statement.setInt(2, basketItemId);
            boolean result;
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                Logger.warn("No rows affected");
                result = false;
            } else {
                result = true;
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.error("Cannot update basket");
            throw new ProductItemDAOException("Cannot update basket");
        }
    }
}
