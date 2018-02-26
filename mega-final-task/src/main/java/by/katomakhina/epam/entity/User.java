package by.katomakhina.epam.entity;

import java.io.Serializable;
import java.util.Objects;

public class User extends Id implements Serializable{
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String role;
    private String password;
    private double balance;
    private boolean banned;
    private Basket basket;

   /* public User() {
    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Double.compare(user.balance, balance) == 0 &&
                banned == user.banned &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email) &&
                Objects.equals(role, user.role) &&
                Objects.equals(password, user.password) &&
                Objects.equals(basket, user.basket);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), firstName, lastName, login, email, role, password, balance, banned, basket);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", banned=" + banned +
                ", balance=" + balance +
                ", role='" + role + '\'' +
                ", basket=" + basket +
                '}';
    }
}
