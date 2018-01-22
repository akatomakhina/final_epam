package by.katomakhina.epam.dao.order;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.entity.Status;

import java.util.List;

public interface OrderDAO {
    public int createOrder(Order order) throws DAOException;
    public void deleteOrdersByUser(int userId) throws DAOException;
    public List<Order> getAllOrdersByID() throws DAOException;
    public List<Order> getAllUserOrders(int userId) throws DAOException;
    public void updateOrderStatus(int id, String status) throws DAOException;
    public List<Status> getAllOrderStatuses() throws DAOException;
}
