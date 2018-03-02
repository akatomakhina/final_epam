package by.katomakhina.epam.service.product.impl;

import by.katomakhina.epam.dao.abstractDAO.AbstractDAOFactory;
import by.katomakhina.epam.dao.exception.DAOException;
import by.katomakhina.epam.dao.exception.ProductDAOException;
import by.katomakhina.epam.dao.exception.ProductItemDAOException;
import by.katomakhina.epam.dao.factory.impl.DAOFactoryImpl;
import by.katomakhina.epam.dao.product.impl.ProductDAOImpl;
import by.katomakhina.epam.dao.product.impl.ProductItemDAOImpl;
import by.katomakhina.epam.entity.Basket;
import by.katomakhina.epam.entity.BasketItem;
import by.katomakhina.epam.entity.Product;
import by.katomakhina.epam.entity.ProductItem;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.product.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private static final Logger Logger = LogManager.getLogger(ProductServiceImpl.class);


    @Override
    public void removeBasketItemById(int id) throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            productItemDAO.removeFromBasketById(id);
        } catch (DAOException e) {
            Logger.error("Cannot remove basket item by id");
            e.printStackTrace();
            throw new ServiceException("Cannot remove basket item by id");
        } catch (ProductItemDAOException e) {
            new ProductItemDAOException("Cannot remove basket item by id");
        }
    }

    @Override
    public void addProductToBasket(int idUser, int idProduct, int amount) throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            ProductItem productItem = new ProductItem();
            Product product = findById(idProduct);
            productItem.setProduct(product);
            productItem.setAmount(amount);
            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            int amountInBasket = productItemDAO.getBasketAmount(idProduct);
            if (amountInBasket != 0) {
                productItemDAO.updateAmountInBasket(amount + amountInBasket, idProduct);
            } else {
                productItemDAO.addToBasket(productItem, idUser);
            }
        } catch (DAOException e) {
            Logger.error("Cannot add product to basket");
            e.printStackTrace();
            throw new ServiceException("Cannot add product to basket");
        } catch (ProductItemDAOException e) {
            new ProductItemDAOException("Cannot add product to basket");
        }
    }

    @Override
    public List<ProductItem> getBasketByUserId(int idUser) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            return productItemDAO.getAllBasketByUserId(idUser);
        } catch (DAOException e) {
            Logger.error("Cannot get basket");
            e.printStackTrace();
            throw new ServiceException("Cannot get basket");
        }
    }

    @Override
    public boolean isAmountValid(int amount, int idProduct) throws ServiceException {
        try {
            if (amount <= 0) {
                return false;
            }
            DAOFactoryImpl factory = new DAOFactoryImpl();
            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            int warehouse = productItemDAO.findAmountByProductWarehouse(idProduct);
            return warehouse >= amount;
        } catch (DAOException e) {
            Logger.error("Cannot check is amount valid");
            e.printStackTrace();
            throw new ServiceException("Cannot check is amount valid");
        }
    }

    @Override
    public List<Product> getAllProduct() throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            ProductDAOImpl ProductDAO = factory.getDAO(ProductDAOImpl.class);
            return ProductDAO.getAllProducts();
        } catch (DAOException e) {
            Logger.error("Cannot get product");
            e.printStackTrace();
            throw new ServiceException("Cannot get product");
        }
    }

    @Override
    public Product findById(int id) throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            ProductDAOImpl ProductDAO = factory.getDAO(ProductDAOImpl.class);
            return (Product) ProductDAO.findById(id, Product.class);
        } catch (DAOException e) {
            Logger.error("Cannot find by product id");
            e.printStackTrace();
            throw new ServiceException("Cannot find by product id");
        }
    }

    @Override
    public int findAmountByProductId(int idProduct) throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            return productItemDAO.findAmountByProductWarehouse(idProduct);
        } catch (DAOException e) {
            Logger.error("Cannot find amount by product id");
            e.printStackTrace();
            throw new ServiceException("Cannot find amount by product id");
        }
    }

    @Override
    public Basket createBasket(List<ProductItem> itemList) throws ServiceException {
        List<BasketItem> basketItemList = new ArrayList<>();
        boolean isValidBasket = true;
        for (ProductItem item : itemList) {
            BasketItem basketItem = new BasketItem();
            basketItem.setId(item.getId());
            basketItem.setProduct(item.getProduct());
            basketItem.setAmount(item.getAmount());
            int inWarehouse = findAmountByProductId(item.getProduct().getId());
            basketItem.setAmountInWarehouse(inWarehouse);
            if (!isAmountValid(item.getAmount(), item.getProduct().getId())) {
                basketItem.setStatus("INVALID");
                isValidBasket = false;
            }
            basketItemList.add(basketItem);
        }
        return new Basket(basketItemList, isValidBasket);
    }

    @Override
    public void createProduct(Product product, int amount) throws ServiceException, DAOException {
        DAOFactoryImpl factory = null;
        try {
            factory = new DAOFactoryImpl();
            factory.startTransaction();
            ProductDAOImpl ProductDAO = factory.getDAO(ProductDAOImpl.class);
            int idProduct = ProductDAO.createProduct(product);
            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            if (amount > 0) {
                productItemDAO.createWarehouseEntry(idProduct, amount);
            }
            factory.commitTransaction();
        } catch (DAOException e) {
            try {
                factory.rollbackTransaction();
                Logger.info("Transaction rollback done");
            } catch (DAOException e1) {
                throw new ServiceException("Cannot rollback transaction");
            }
            Logger.error("Cannot create new product");
            e.printStackTrace();
            throw new ServiceException("Cannot create new product");

        } catch (ProductItemDAOException e) {
            new ProductItemDAOException("Cannot create new product");
        } finally {
            factory.closeConnection();
        }
    }

    @Override
    public boolean isProductExist(String name) throws ServiceException {
        try {
            boolean result = false;
            AbstractDAOFactory factory = new DAOFactoryImpl();
            ProductDAOImpl ProductDAO = factory.getDAO(ProductDAOImpl.class);
            Product product = ProductDAO.findProductByName(name);
            if (product.getId() != 0) {
                result = true;
            }
            return result;
        } catch (DAOException e) {
            Logger.error("Cannot find out if product exist");
            e.printStackTrace();
            throw new ServiceException("Cannot find out if product exist");
        }
    }

    @Override
    public void deleteProduct(int idProduct) throws ServiceException, DAOException {
        DAOFactoryImpl factory = null;
        try {
            factory = new DAOFactoryImpl();
            ProductDAOImpl productDAO = factory.getDAO(ProductDAOImpl.class);
            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            factory.startTransaction();
            productItemDAO.deleteFromWarehouseByProduct(idProduct);
            productItemDAO.deleteFromBasketByProduct(idProduct);
            productItemDAO.deleteOrderItemsByProduct(idProduct);
            productDAO.deleteProduct(idProduct);
            factory.commitTransaction();
        } catch (DAOException e) {
            try {
                factory.rollbackTransaction();
                Logger.info("Transaction rollback done");
            } catch (DAOException e1) {
                Logger.error("Cannot rollback transaction");
                e.printStackTrace();
                throw new ServiceException("Cannot rollback transaction");
            }
            Logger.error("Cannot delete product");
            throw new ServiceException("Cannot delete product");
        } catch (ProductItemDAOException e) {
            new ProductItemDAOException("Cannot delete product");
        } catch (ProductDAOException e) {
            new ProductDAOException("Cannot delete product");
        } finally {
            factory.closeConnection();
        }
    }

    @Override
    public boolean updateProduct(Product product, int amount) throws ServiceException, DAOException {
        DAOFactoryImpl factory = null;
        boolean result;
        try {
            factory = new DAOFactoryImpl();
            ProductDAOImpl productDAO = factory.getDAO(ProductDAOImpl.class);
            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            ProductItem productItem = new ProductItem(product, amount);
            Product baseProduct = findById(product.getId());
            int baseAmount = productItemDAO.findAmountByProductWarehouse(product.getId());
            ProductItem baseItem = new ProductItem(baseProduct, baseAmount);
            if (productItem.equals(baseItem)) {
                return false;
            }
            factory.startTransaction();
            int affectedRowsProduct = productDAO.updateProduct(product);
            int affectedRowsWarehouse = productItemDAO.updateAmountInWarehouse(amount, product.getId());
            factory.commitTransaction();
            result = !(affectedRowsProduct == 0 && affectedRowsWarehouse == 0);
            return result;
        } catch (DAOException e) {
            try {
                factory.rollbackTransaction();
                Logger.info("Transaction rollback done");
            } catch (DAOException e1) {
                Logger.error("Cannot rollback done");
                throw new ServiceException("Cannot rollback transaction");
            }
            Logger.error("Cannot update product");
            e.printStackTrace();
            throw new ServiceException("Cannot update product");
        } finally {
            factory.closeConnection();
        }
    }

    @Override
    public List<Product> getSubcatalogProduct(int idProduct) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            ProductDAOImpl ProductDAO = factory.getDAO(ProductDAOImpl.class);
            return ProductDAO.getSubcatalogProduct(idProduct);
        } catch (DAOException e) {
            Logger.error("Cannot get subcatalog by product");
            e.printStackTrace();
            throw new ServiceException("Cannot get subcatalog by product");
        } catch (ProductDAOException e) {
            Logger.error("Cannot get subcatalog by product");
            e.printStackTrace();
            throw new ServiceException("Cannot get subcatalog by product");
        }
    }

    @Override
    public boolean updateBasketItem(Integer basketItemId, Integer amount) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            ProductItemDAOImpl productItemDAO = factory.getDAO(ProductItemDAOImpl.class);
            return productItemDAO.updateBasketItemById(basketItemId, amount);
        } catch (DAOException e) {
            Logger.error("Cannot get subcategory by product");
            e.printStackTrace();
            throw new ServiceException("Cannot get subcategory by product");
        } catch (ProductItemDAOException e) {
            Logger.error("Cannot get subcategory by product");
            e.printStackTrace();
            throw new ServiceException("Cannot get subcategory by product");
        }
    }
}
