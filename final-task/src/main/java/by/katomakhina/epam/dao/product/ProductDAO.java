package by.katomakhina.epam.dao.product;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.ProductDAOException;
import by.katomakhina.epam.entity.Product;

import java.util.List;

public interface ProductDAO {
    public List<Product> getAllProducts() throws ProductDAOException;
    public int create(Product product) throws DAOException;
    public Product findProductByName(String name) throws DAOException;
    public void deleteProduct(int id) throws ProductDAOException;
    public int updateProduct(Product product) throws ProductDAOException;
    public List<Product> getSubcategoryProduct(int id) throws ProductDAOException;
}
