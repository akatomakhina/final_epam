package by.katomakhina.epam.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Basket extends Id implements Serializable {
    private List<BasketItem> basketItems;
    boolean isValid;

    public Basket() {
    }

    public Basket(List<BasketItem> basketItems, boolean isValid) {
        this.basketItems = basketItems;
        this.isValid = isValid;
    }

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Basket basket = (Basket) o;
        return isValid == basket.isValid &&
                Objects.equals(basketItems, basket.basketItems);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), basketItems, isValid);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "basketItems=" + basketItems +
                ", isValid=" + isValid +
                '}';
    }
}
