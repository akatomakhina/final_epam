package by.katomakhina.epam.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Order extends Id implements Serializable {
    private List<ProductItem> productItem;
    private int amount;
    private Timestamp date;
    private Status status;
    private int idUser;

    public Order() {
    }

    public List<ProductItem> getProductItem() {
        return productItem;
    }

    public void setProductItem(List<ProductItem> productItem) {
        this.productItem = productItem;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return amount == order.amount &&
                idUser == order.idUser &&
                Objects.equals(productItem, order.productItem) &&
                Objects.equals(date, order.date) &&
                Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), productItem, amount, date, status, idUser);
    }

    @Override
    public String toString() {
        return "Order{" +
                "productItem=" + productItem +
                ", amount=" + amount +
                ", date=" + date +
                ", status=" + status +
                ", idUser=" + idUser +
                '}';
    }
}
