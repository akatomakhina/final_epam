package by.katomakhina.epam.dao.product;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.ProductItemDAOException;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.entity.ProductItem;

import java.util.List;

public interface ProductItemDAO {
    public List<ProductItem> getAllBasketByUserId(int idUser) throws DAOException;
    public void removeFromBasketById(int id) throws ProductItemDAOException;
    public void addToBasket(ProductItem item, int idUser) throws ProductItemDAOException;
    public int findAmountByProductWarehouse(int idProduct) throws DAOException;
    public int updateAmountInWarehouse(int quantity, int idProduct) throws DAOException;
    public void createOrderItem(ProductItem ProductItem, int idOrder) throws DAOException;
    //public void deleteFromWarehouse(int productId) throws ProductItemDAOException;
    public void deleteBasketByUser(int idUser) throws DAOException;
    public void deleteOrderItemById(int idUser) throws ProductItemDAOException;
    public void updateAmountInBasket(int quantity, int idProduct) throws ProductItemDAOException;
    public int getBasketAmount(int idProduct) throws ProductItemDAOException;
    public void deleteFromWarehouseByProduct(int idProduct) throws ProductItemDAOException;
    public void createWarehouseEntry(int idProduct, int amount) throws ProductItemDAOException;
    public void deleteFromBasketByProduct(int idProduct) throws ProductItemDAOException;
    public void deleteOrderItemsByProduct(int idProduct) throws ProductItemDAOException;
    public List<ProductItem> getItemsByOrder(Order order) throws ProductItemDAOException;
    public boolean updateBasketItemById(Integer basketItemId, Integer amount) throws ProductItemDAOException;
}

