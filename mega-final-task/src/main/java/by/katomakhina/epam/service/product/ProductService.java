package by.katomakhina.epam.service.product;

import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.entity.Basket;
import by.katomakhina.epam.entity.Product;
import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.service.exception.ServiceException;

import java.util.List;

public interface ProductService {
    public void removeBasketItemById(int id) throws ServiceException;
    public void addProductToBasket(int idUser, int idProduct, int amount) throws ServiceException;
    public List<ProductItem> getBasketByUserId(int idUser) throws ServiceException;
    public boolean isAmountValid(int amount, int idProduct) throws ServiceException;
    public List<Product> getAllProduct() throws ServiceException;
    public Product findById(int id) throws ServiceException;
    public int findAmountByProductId(int idProduct) throws ServiceException;
    public Basket createBasket(List<ProductItem> itemList) throws ServiceException;
    public void createProduct(Product product, int amount) throws ServiceException, DAOException;
    public boolean isProductExist(String name) throws ServiceException;
    public void deleteProduct(int idProduct) throws ServiceException, DAOException;
    public boolean updateProduct(Product product, int amount) throws ServiceException, DAOException;
    public List<Product> getSubcatalogProduct(int idProduct) throws ServiceException;
    public boolean updateBasketItem(Integer basketItemId, Integer amount) throws ServiceException;
}
