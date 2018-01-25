package by.katomakhina.epam.dao.product;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.ProductItemDAOException;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.entity.ProductItem;

import java.util.List;

public interface ProductItemDAO {
    public List<ProductItem> getAllBasketByUserId(int userId) throws DAOException;
    public void removeFromBasketById(int id) throws ProductItemDAOException;
    public void addToBasket(ProductItem item, int userId) throws ProductItemDAOException;
    public int findQuantityByProductWarehouse(int productId) throws ProductItemDAOException;
    public int updateQuantityInWarehouse(int quantity, int productId) throws ProductItemDAOException;
    public void createOrderItem(ProductItem ProductItem, int orderID) throws ProductItemDAOException;
    public void deleteFromWarehouse(int productId) throws ProductItemDAOException;
    public void deleteBasketByUser(int userId) throws ProductItemDAOException;
    public void deleteOrderItemById(int userId) throws ProductItemDAOException;
    public void updateQuantityInCart(int quantity, int ProductId) throws ProductItemDAOException;
    public int getCartQuantity(int ProductId) throws ProductItemDAOException;
    //public void deleteFromWareHouseByProduct(int ProductId) throws ProductItemDAOException;
    public void createWarehouseEntry(int ProductId, int quantity) throws ProductItemDAOException;
    public void deleteFromBasketByProduct(int ProductId) throws ProductItemDAOException;
    public void deleteOrderItemsByProduct(int ProductId) throws ProductItemDAOException;
    public List<ProductItem> getItemsByOrder(Order order) throws ProductItemDAOException;
    public boolean updateBasketItemById(Integer cartItemId, Integer quantity) throws ProductItemDAOException;
}

