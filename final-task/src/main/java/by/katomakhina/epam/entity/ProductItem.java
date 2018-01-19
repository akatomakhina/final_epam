package by.katomakhina.epam.entity;

public class ProductItem extends Id{
    Product product;
    private int amount;

    public ProductItem() {
    }

    public ProductItem(Product product, int amount) {
        this.product = product;
        this.amount = amount;
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

    @Override
    public boolean equals(Object o) {
        if (this==o) return  true;
        if (!(o instanceof ProductItem)) return false;
        ProductItem productItem = (ProductItem) o;
        return this.product.getTitle().equals(productItem.getProduct().getTitle())
                && this.product.getPrice() == productItem.getProduct().getPrice()
                && this.product.getId_catalog() == productItem.getProduct().getId_catalog()
                && this.product.getDescription().equals(productItem.getProduct().getDescription())
                && this.amount == productItem.amount;
    }
}
