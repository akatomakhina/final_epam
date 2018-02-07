package by.katomakhina.epam.service.order.impl;

import by.katomakhina.epam.dao.abstractDAO.AbstractDAOFactory;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.ProductItemDAOException;
import by.katomakhina.epam.dao.factory.impl.DAOFactoryImpl;
import by.katomakhina.epam.dao.order.impl.OrderDAOImpl;
import by.katomakhina.epam.dao.product.impl.ProductItemDAOImpl;
import by.katomakhina.epam.dao.user.impl.UserDAOImpl;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.entity.Status;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.order.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger Logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public Order createOrder(List<ProductItem> itemList, int idUser, boolean isFromBalance) throws ServiceException, DAOException {
        DAOFactoryImpl factoryJdbc = null;
        try {
            factoryJdbc = new DAOFactoryImpl();
            factoryJdbc.startTransaction();
            Order order = new Order();
            order.setDate(getDate());
            OrderDAOImpl orderDao = factoryJdbc.getDAO(OrderDAOImpl.class);
            ProductItemDAOImpl itemDao = factoryJdbc.getDAO(ProductItemDAOImpl.class);
            int totalAmount = getTotalAmount(itemList);
            order.setAmount(totalAmount);
            order.setIdUser(idUser);
            int orderId = orderDao.createOrder(order);
            order.setProductItem(itemList);
            order.setId(orderId);

            for (ProductItem productItem : itemList) {
                int ProductId = productItem.getProduct().getId();
                int quantityInWarehouse = itemDao.findAmountByProductWarehouse(productItem.getProduct().getId());
                itemDao.createOrderItem(productItem, orderId);
                int result = quantityInWarehouse - productItem.getAmount();
                itemDao.updateAmountInWarehouse(result, ProductId);
            }
            if (isFromBalance){
                UserDAOImpl userDAO = factoryJdbc.getDAO(UserDAOImpl.class);
                double balanceAfterTransaction = userDAO.getUserBalance(idUser) - totalAmount;
                userDAO.updateBalance((int) balanceAfterTransaction, idUser);
            }
            itemDao.deleteBasketByUser(idUser);
            factoryJdbc.commitTransaction();
            return order;
        } catch (DAOException e) {
            try {
                factoryJdbc.rollbackTransaction();
            } catch (DAOException e1) {
                throw new ServiceException("Cannot rollback transaction");
            }
            e.printStackTrace();
            throw new ServiceException("Cannot create single-item order");

        } finally {
            factoryJdbc.closeConnection();
        }
    }

    @Override
    public List<Status> getStatuses() throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            OrderDAOImpl orderDAO = factory.getDAO(OrderDAOImpl.class);
            return orderDAO.getAllOrderStatuses();
        } catch (DAOException e) {
            Logger.error("Cannot get order statuses");
            e.printStackTrace();
            throw new ServiceException("Cannot get order statuses");
        }
    }

    @Override
    public List<Order> getAllUserOrders(int idUser) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            OrderDAOImpl orderDao = factory.getDAO(OrderDAOImpl.class);
            return orderDao.getAllUserOrders(idUser);
        } catch (DAOException e) {
            Logger.error("Cannot get all orders");
            e.printStackTrace();
            throw new ServiceException("Cannot get all orders");
        }
    }

    @Override
    public List<Order> getAllOrders() throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            OrderDAOImpl orderDao = factory.getDAO(OrderDAOImpl.class);
            return orderDao.getAllOrdersByID();
        } catch (DAOException e) {
            throw new ServiceException("Cannot get all orders");
        }
    }

    @Override
    public void setOrderStatus(int id, String status) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            OrderDAOImpl userDAO = factory.getDAO(OrderDAOImpl.class);
            userDAO.updateOrderStatus(id, status);
        } catch (DAOException e) {
            throw new ServiceException("Cannot set order status");
        }
    }

    @Override
    public Order getOrderById(int id) throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            OrderDAOImpl orderDAO = factory.getDAO(OrderDAOImpl.class);
            return (Order) orderDAO.findById(id, Order.class); //!!!!!!!! CAST
        } catch (DAOException e) {
            throw new ServiceException("Cannot get order by id");
        }
    }

    @Override
    public List<ProductItem> getItemsByOrder(Order order) throws ServiceException, DAOException {
        DAOFactoryImpl factory = null;
        List<ProductItem> itemList = new ArrayList<>();
        try {

            factory = new DAOFactoryImpl();
            ProductItemDAOImpl itemDAO = factory.getDAO(ProductItemDAOImpl.class);
            itemList = itemDAO.getItemsByOrder(order);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ProductItemDAOException e) {
            e.printStackTrace();
        } finally {
            factory.closeConnection();
        }
        return itemList;
    }

    @Override
    public int getTotalAmount(List<ProductItem> list) throws ServiceException {
        int result = 0;
        for (ProductItem item : list) {
            int price = (int) (item.getProduct().getPrice() * item.getAmount()); //CAST
            result = result + price;
        }
        return result;
    }

    private Timestamp getDate() throws ServiceException { //PRIVATE
        Date date = new java.util.Date();
        return new Timestamp(date.getTime());
    }
}
