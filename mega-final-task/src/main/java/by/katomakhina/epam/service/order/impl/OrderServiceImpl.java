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
    public Order createOrder(List<ProductItem> productItemList, int idUser, boolean isFromBalance) throws ServiceException, DAOException {
        DAOFactoryImpl factory = null;
        try {
            factory = new DAOFactoryImpl();
            factory.startTransaction();

            Order order = new Order();
            order.setDate(getDate());

            OrderDAOImpl orderDAO = factory.getDAO(OrderDAOImpl.class);

            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            double totalAmount = getTotalAmount(productItemList);
            order.setAmount(totalAmount);
            order.setIdUser(idUser);
            int orderId = orderDAO.createOrder(order);
            order.setProductItem(productItemList);
            order.setId(orderId);

            for (ProductItem productItem : productItemList) {
                int ProductId = productItem.getProduct().getId();
                int amountInWarehouse = productItemDAO.findAmountByProductWarehouse(productItem.getProduct().getId());
                productItemDAO.createOrderItem(productItem, orderId);
                int result = amountInWarehouse - productItem.getAmount();
                productItemDAO.updateAmountInWarehouse(result, ProductId);
            }
            if (isFromBalance){
                UserDAOImpl userDAO = factory.getDAO(UserDAOImpl.class);
                double balanceAfterTransaction = userDAO.getUserBalance(idUser) - totalAmount;
                userDAO.updateBalance((int) balanceAfterTransaction, idUser);
            }
            productItemDAO.deleteBasketByUser(idUser);
            factory.commitTransaction();
            return order;
        } catch (DAOException e) {
            try {
                factory.rollbackTransaction();
            } catch (DAOException e1) {
                throw new ServiceException("Cannot rollback transaction");
            }
            e.printStackTrace();
            throw new ServiceException("Cannot create item order");

        } finally {
            factory.closeConnection();
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
            return orderDao.getAllOrdersById();
        } catch (DAOException e) {
            throw new ServiceException("Cannot get all orders");
        }
    }

    @Override
    public void setOrderStatus(int idOrder, String status) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            OrderDAOImpl userDAO = factory.getDAO(OrderDAOImpl.class);
            userDAO.updateOrderStatus(idOrder, status);
        } catch (DAOException e) {
            throw new ServiceException("Cannot set order status");
        }
    }

    @Override
    public Order getOrderById(int idOrder) throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            OrderDAOImpl orderDAO = factory.getDAO(OrderDAOImpl.class);
            return (Order) orderDAO.findById(idOrder, Order.class); //!!!!!!!! CAST
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
    public double getTotalAmount(List<ProductItem> productList) {
        double result = 0;
        for (ProductItem item : productList) {
            double price = (item.getProduct().getPrice() * item.getAmount()); //CAST
            result = result + price;
        }
        return result;
    }


    private Timestamp getDate(){ //PRIVATE
        Date date = new java.util.Date();
        return new Timestamp(date.getTime());
    }
}
