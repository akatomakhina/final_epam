package by.katomakhina.epam.controller.entity;

import java.util.Objects;

public class View {
    private String name;
    private boolean isRedirect = false;

    public View(String name) {
        this.name = name;
    }

    public View(String name, boolean isRedirect) {
        this.isRedirect = isRedirect;
        this.name = name;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view = (View) o;
        return isRedirect == view.isRedirect &&
                Objects.equals(name, view.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, isRedirect);
    }

    @Override
    public String toString() {
        return "View{" +
                "name='" + name + '\'' +
                ", isRedirect=" + isRedirect +
                '}';
    }
}
