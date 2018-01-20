package by.katomakhina.epam.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Catalog extends Id implements Serializable {
    private String name;
    List<Subcatalog> subCatalog;

    public Catalog() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subcatalog> getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(List<Subcatalog> subCatalog) {
        this.subCatalog = subCatalog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(name, catalog.name) &&
                Objects.equals(subCatalog, catalog.subCatalog);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, subCatalog);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "name='" + name + '\'' +
                ", subCatalog=" + subCatalog +
                '}';
    }
}
