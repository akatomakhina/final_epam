package by.katomakhina.epam.dao.order.impl;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.factory.EntityFactory;
import by.katomakhina.epam.dao.id.impl.IdDAOImpl;
import by.katomakhina.epam.dao.order.OrderDAO;
import by.katomakhina.epam.entity.Order;
import by.katomakhina.epam.entity.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl extends IdDAOImpl implements OrderDAO {
    private static final Logger Logger = LogManager.getLogger(EntityFactory.class);

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int createOrder(Order order) throws DAOException {
        try {
            String query = getQuery("CREATE_ORDER");
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setTimestamp(1, order.getDate());
            statement.setInt(2, order.getIdUser());
            statement.setInt(3, order.getAmount());
            Integer affectedRows = statement.executeUpdate();
            Logger.info("Rows affected in 'orders'" + affectedRows.toString());
            return generatedKeyGetter(statement);
        } catch (SQLException e) {
            Logger.error("Cannot create order");
            e.printStackTrace();
            throw new DAOException("Cannot create order");
        }
    }

    @Override
    public void deleteOrdersByUser(int userId) throws DAOException {
        try {
            String query = getQuery("DELETE_USER_ORDERS");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.error("Cannot delete from warehouse");
            e.printStackTrace();
            throw new DAOException("Cannot delete from warehouse");
        }
    }

    @Override
    public List<Order> getAllOrdersByID() throws DAOException {
        List<Order> orders = new ArrayList<>();
        try {
            String query = getQuery("ORDERS_GET_ALL");
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setDate(resultSet.getTimestamp("order_date"));
                order.setAmount(resultSet.getInt("total_amount"));
                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setName("status_name");
                order.setStatus(status);
                order.setIdUser(resultSet.getInt("user_id"));
                orders.add(order);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            Logger.error("Cannot get all orders from base");
            throw new DAOException("Cannot get all orders from base");
        }
        return orders;
    }

    @Override
    public List<Order> getAllUserOrders(int userId) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try {
            String query = getQuery("ORDERS_GET_BY_USER_ID");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setDate(resultSet.getTimestamp("order_date"));
                order.setAmount(resultSet.getInt("total_amount"));
                Status status = new Status();
                status.setName(resultSet.getString("status"));
                order.setStatus(status);
                orders.add(order);

            }

        } catch (SQLException e) {
            Logger.error("Cannot get all orders from base");
            e.printStackTrace();
            throw new DAOException("Cannot get all orders from base");
        }
        return orders;
    }

    @Override
    public void updateOrderStatus(int id, String status) throws DAOException {
        try {
            String query = getQuery("UPDATE_ORDER_STATUS");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status);
            statement.setInt(2, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            Logger.error("Cannot update product item in warehouse");
            throw new DAOException("Cannot update product item in warehouse");
        }
    }

    @Override
    public List<Status> getAllOrderStatuses() throws DAOException {
        try {
            String query = getQuery("STATUSES_GET_ALL");
            List<Status> statusList = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Status status = new Status();
                status.setId(resultSet.getInt("id"));
                status.setName(resultSet.getString("name"));
                statusList.add(status);
            }
            return statusList;
        } catch (SQLException e) {
            Logger.error("Cannot get statuses");
            e.printStackTrace();
            throw new DAOException("Cannot get statuses");
        }
    }
}
