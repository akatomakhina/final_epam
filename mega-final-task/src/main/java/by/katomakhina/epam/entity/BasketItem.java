package by.katomakhina.epam.entity;

import java.io.Serializable;
import java.util.Objects;

public class BasketItem extends Id implements Serializable {
    private Product product;
    private int amount;
    private String status;
    private int amountInWarehouse;

    public BasketItem() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAmountInWarehouse() {
        return amountInWarehouse;
    }

    public void setAmountInWarehouse(int amountInWarehouse) {
        this.amountInWarehouse = amountInWarehouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BasketItem that = (BasketItem) o;
        return amount == that.amount &&
                amountInWarehouse == that.amountInWarehouse &&
                Objects.equals(product, that.product) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), product, amount, status, amountInWarehouse);
    }

    @Override
    public String toString() {
        return "BasketItem{" +
                "product=" + product +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", amountInWarehouse=" + amountInWarehouse +
                '}';
    }
}
