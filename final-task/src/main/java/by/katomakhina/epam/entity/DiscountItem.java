package by.katomakhina.epam.entity;

import java.io.Serializable;
import java.util.Objects;

public class DiscountItem extends Id implements Serializable{
    private double size;

    public DiscountItem() {
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiscountItem that = (DiscountItem) o;
        return Double.compare(that.size, size) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), size);
    }

    @Override
    public String toString() {
        return "DiscountItem{" +
                "size=" + size +
                '}';
    }
}
