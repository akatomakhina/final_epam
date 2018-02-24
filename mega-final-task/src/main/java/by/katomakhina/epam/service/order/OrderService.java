package by.katomakhina.epam.service.order;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.entity.Status;
import by.katomakhina.epam.service.exception.ServiceException;

import java.sql.Timestamp;
import java.util.List;

public interface OrderService {
    public Order createOrder(List<ProductItem> itemList, int idUser, boolean isFromBalance) throws ServiceException, DAOException;
    public List<Status> getStatuses() throws ServiceException;
    public List<Order> getAllUserOrders(int idUser) throws ServiceException;
    public List<Order> getAllOrders() throws ServiceException;
    public void setOrderStatus(int id, String status) throws ServiceException;
    public Order getOrderById(int id) throws ServiceException;
    public List<ProductItem> getItemsByOrder(Order order) throws ServiceException, DAOException;
    public double getTotalAmount(List<ProductItem> list);
}
