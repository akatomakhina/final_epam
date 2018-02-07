package by.katomakhina.epam.entity;

import java.io.Serializable;
import java.util.Objects;

public class Subcatalog extends Id implements Serializable {
    int id;
    String name;

    public Subcatalog() {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subcatalog that = (Subcatalog) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name);
    }

    @Override
    public String toString() {
        return "Subcatalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
