package by.katomakhina.epam.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Discount extends Id implements Serializable {
    private List<DiscountItem> discountItem;
    private int userId;

    public Discount() {
    }

    public List<DiscountItem> getDiscountItem() {
        return discountItem;
    }

    public void setDiscountItem(List<DiscountItem> discountItem) {
        this.discountItem = discountItem;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Discount discount = (Discount) o;
        return userId == discount.userId &&
                Objects.equals(discountItem, discount.discountItem);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), discountItem, userId);
    }

    @Override
    public String toString() {
        return "Discount{" +
                "discountItem=" + discountItem +
                ", userId=" + userId +
                '}';
    }
}
