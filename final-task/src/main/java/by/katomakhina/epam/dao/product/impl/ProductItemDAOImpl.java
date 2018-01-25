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
    public List<ProductItem> getAllBasketByUserId(int userId) throws DAOException {
        try {
            String query = getQuery("GET_BASKET_BY_USER");
            List<ProductItem> itemList = new ArrayList<>();
            DAOFactoryImpl factoryJdbc = new DAOFactoryImpl();
            ProductDAOImpl ProductDAO = factoryJdbc.getDAO(ProductDAOImpl.class);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductItem item = new ProductItem();
                item.setId(resultSet.getInt("id"));
                int ProductId = resultSet.getInt("product_id");
                item.setProduct((Product) ProductDAO.findById(ProductId, Product.class)); //!!!!!!!!!!!!
                item.setAmount(resultSet.getInt("quantity"));
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
    public void addToBasket(ProductItem item, int userId) throws ProductItemDAOException {
        try {
            String query = getQuery("ADD_PRODUCT_TO_BASKET");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, item.getProduct().getId());
            statement.setInt(3, item.getAmount());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                Logger.error("No rows affected");
                throw new SQLException("No rows affected");
            }

        } catch (SQLException e) {
            Logger.error("Cannot add Product to basket");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot add product to basket");
        }
    }

    @Override
    public int findQuantityByProductWarehouse(int productId) throws ProductItemDAOException {
        try {
            ResourceBundle resource = ResourceBundle.getBundle("inquiry");
            String query = resource.getString("ITEM_GET_QUANTITY");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            int quantity = 0;
            while (resultSet.next()) {
                quantity = resultSet.getInt("quantity");
            }
            return quantity;
        } catch (SQLException e) {
            Logger.error("Cannot find quantity by product");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot find quantity by product");
        }
    }

    @Override
    public int updateQuantityInWarehouse(int quantity, int productId) throws ProductItemDAOException {
        try {
            String query = getQuery("UPDATE_WAREHOUSE_QUANTITY");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            int affectedRows = statement.executeUpdate();
            return affectedRows;
        } catch (SQLException e) {
            Logger.error("Cannot update Product item in warehouse", e);
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot update Product item in warehouse");
        }
    }

    @Override
    public void createOrderItem(ProductItem ProductItem, int orderID) throws ProductItemDAOException {
        try {
            String query = getQuery("CREATE_ITEM");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ProductItem.getProduct().getId());
            statement.setInt(2, orderID);
            statement.setInt(3, ProductItem.getAmount());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

        } catch (SQLException e) {
            Logger.error("Cannot add order item to database", e);
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot add order item to database");
        }
    }

    @Override
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
    }


   /* @Override
    public void deleteFromWareHouseByProduct(int ProductId) throws ProductItemDAOException {
        try {
            String query = getQuery("DELETE_FROM_WAREHOUSE_BY_PRODUCT");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ProductId);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.error("Cannot delete from warehouse");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot delete from warehouse");
        }
    }*/

    @Override
    public void deleteBasketByUser(int userId) throws ProductItemDAOException {
        try {
            String query = getQuery("DELETE_USER_CART");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.error("Cannot delete from warehouse");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot delete from warehouse");
        }
    }

    @Override
    public void deleteOrderItemById(int userId) throws ProductItemDAOException {
        try {
            String query = getQuery("DELETE_USER_ITEMS");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.error("Cannot delete orderItems");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot delete orderItems");
        }
    }

    @Override
    public void updateQuantityInCart(int quantity, int ProductId) throws ProductItemDAOException {
        try {
            String query = getQuery("UPDATE_CART_QUANTITY");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, quantity);
            statement.setInt(2, ProductId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("adding to basket, no rows affected.");
            }
        } catch (SQLException e) {
            Logger.error("Cannot update cart item in warehouse");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot update basket item in warehouse");
        }
    }

    @Override
    public int getCartQuantity(int ProductId) throws ProductItemDAOException {
        try {
            String query = getQuery("CART_ITEM_QUANTITY");
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, ProductId);
            ResultSet resultSet = statement.executeQuery();
            int quantity = 0;
            while (resultSet.next()) {
                quantity = resultSet.getInt("quantity");
            }
            return quantity;
        } catch (SQLException e) {
            Logger.error("Cannot find quantity by product");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot find quantity by product");
        }
    }



    @Override
    public void createWarehouseEntry(int ProductId, int quantity) throws ProductItemDAOException {
        try {
            String query = getQuery("CREATE_WAREHOUSE_ENTRY");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ProductId);
            statement.setInt(2, quantity);
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
    public void deleteFromBasketByProduct(int ProductId) throws ProductItemDAOException {
        try {
            String query = getQuery("DELETE_FROM_BASKET_BY_PRODUCT");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ProductId);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.error("Cannot delete from basket");
            e.printStackTrace();
            throw new ProductItemDAOException("Cannot delete from basket");
        }
    }

    @Override
    public void deleteOrderItemsByProduct(int ProductId) throws ProductItemDAOException {
        try {
            String query = getQuery("DELETE_ORDER_ITEMS_BY_PRODUCT");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ProductId);
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
            String query = getQuery("ITEM_GET_BY_ORDER_ID");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, order.getId());
            ResultSet resultSet = statement.executeQuery();
            List<ProductItem> list = new ArrayList<>();
            while (resultSet.next()) {
                ProductItem item = new ProductItem();
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setPrice(resultSet.getInt("product_price"));
                product.setTitle(resultSet.getString("product_name"));
                item.setProduct(product);
                item.setAmount(resultSet.getInt("quantity"));
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
    public boolean updateBasketItemById(Integer cartItemId, Integer quantity) throws ProductItemDAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("UPDATE_CART_ITEM_BY_ID"));
            statement.setInt(1, quantity);
            statement.setInt(2, cartItemId);
            boolean result;
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                Logger.warn("no rows affected");
                result = false;
            } else {
                result = true;
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.error("Cannot update cart");
            throw new ProductItemDAOException("Cannot update cart");
        }
    }
}
