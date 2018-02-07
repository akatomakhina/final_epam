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

public class ProductServiceImpl implements ProductService{

    private static final Logger Logger = LogManager.getLogger(ProductServiceImpl.class);


    @Override
    public void removeBasketItemById(int id) throws ServiceException {
        try {
            DAOFactoryImpl factoryJdbc = new DAOFactoryImpl();
            ProductItemDAOImpl itemDAO = factoryJdbc.getDAO(ProductItemDAOImpl.class);
            itemDAO.removeFromBasketById(id);
        } catch (DAOException e) {
            Logger.error("Cannot remove basket item by id");
            e.printStackTrace();
            throw new ServiceException("Cannot remove basket item by id");
        } catch (ProductItemDAOException e) {
            new ProductItemDAOException("Cannot remove basket item by id");
        }
    }

    @Override
    public void addProductToCart(int idUser, int idProduct, int amount) throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            ProductItem item = new ProductItem();
            Product product = findById(idProduct);
            item.setProduct(product);
            item.setAmount(amount);
            ProductItemDAOImpl itemDAO = factory.getDAO(ProductItemDAOImpl.class);
            int quantityInCart = itemDAO.getBasketAmount(idProduct);
            if (quantityInCart != 0) {
                itemDAO.updateAmountInBasket(amount + quantityInCart, idProduct);
            } else {
                itemDAO.addToBasket(item, idUser);
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
            AbstractDAOFactory daoFactoryJdbc = new DAOFactoryImpl();
            ProductItemDAOImpl itemDao = daoFactoryJdbc.getDAO(ProductItemDAOImpl.class);
            return itemDao.getAllBasketByUserId(idUser);
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
            DAOFactoryImpl factoryJdbc = new DAOFactoryImpl();
            ProductItemDAOImpl dao = factoryJdbc.getDAO(ProductItemDAOImpl.class);
            int warehouse = dao.findAmountByProductWarehouse(idProduct);
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
            Logger.error("Cannot get Product");
            e.printStackTrace();
            throw new ServiceException("Cannot get Product");
        }
    }

    @Override
    public Product findById(int id) throws ServiceException {
        try {
            DAOFactoryImpl factory = new DAOFactoryImpl();
            ProductDAOImpl ProductDAO = factory.getDAO(ProductDAOImpl.class);
            return (Product) ProductDAO.findById(id, Product.class); //!!!!!!!!! Cast
        } catch (DAOException e) {
            Logger.error("Cannot find by id");
            e.printStackTrace();
            throw new ServiceException("Cannot find by id");
        }
    }

    @Override
    public int findAmountByProductId(int id) throws ServiceException {
        try {
            DAOFactoryImpl factoryJdbc = new DAOFactoryImpl();
            ProductItemDAOImpl itemDao = factoryJdbc.getDAO(ProductItemDAOImpl.class);
            return itemDao.findAmountByProductWarehouse(id);
        } catch (DAOException e) {
            Logger.error("Cannot find quantity by id");
            e.printStackTrace();
            throw new ServiceException("Cannot find quantity by id");
        }
    }

    @Override
    public Basket createBasket(List<ProductItem> itemList) throws ServiceException {
        List<BasketItem> cartItems = new ArrayList<>();
        boolean isValidCart = true;
        for (ProductItem item : itemList) {
            BasketItem cartItem = new BasketItem();
            cartItem.setId(item.getId());
            cartItem.setProduct(item.getProduct());
            cartItem.setAmount(item.getAmount());
            int inWarehouse = findAmountByProductId(item.getProduct().getId());
            cartItem.setAmountInWarehouse(inWarehouse);
            if (!isAmountValid(item.getAmount(), item.getProduct().getId())) {
                cartItem.setStatus("INVALID");
                isValidCart = false;
            }
            cartItems.add(cartItem);
        }
        return new Basket(cartItems, isValidCart);
    }

    @Override
    public void createProduct(Product product, int amount) throws ServiceException, DAOException {
        DAOFactoryImpl factory = null;
        try {
            factory = new DAOFactoryImpl();
            factory.startTransaction();
            ProductDAOImpl ProductDAO = factory.getDAO(ProductDAOImpl.class);
            int idProduct = ProductDAO.create(product);
            ProductItemDAOImpl itemDAO = factory.getDAO(ProductItemDAOImpl.class);
            if (amount > 0) {
                itemDAO.createWarehouseEntry(idProduct, amount);

            }
            factory.commitTransaction();
        } catch (DAOException e) {
            try {
                factory.rollbackTransaction();
                Logger.info("transaction rollback done");
            } catch (DAOException e1) {
                throw new ServiceException("Cannot create");
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
    public void deleteProduct(int id) throws ServiceException, DAOException {
        DAOFactoryImpl factory = null;
        try {
            factory = new DAOFactoryImpl();
            ProductDAOImpl ProductDAO = factory.getDAO(ProductDAOImpl.class);
            ProductItemDAOImpl itemDAO = factory.getDAO(ProductItemDAOImpl.class);
            factory.startTransaction();
            itemDAO.deleteFromWarehouseByProduct(id);
            itemDAO.deleteFromBasketByProduct(id);
            itemDAO.deleteOrderItemsByProduct(id);
            ProductDAO.deleteProduct(id);
            factory.commitTransaction();
        } catch (DAOException e) {
            try {
                factory.rollbackTransaction();
                Logger.info("transaction rollback done");
            } catch (DAOException e1) {
                Logger.error("Cannot rollback transaction");
                e.printStackTrace();
                throw new ServiceException("Cannot rollback transaction");
            }
            Logger.error("Cannot delete product");
            throw new ServiceException("Cannot delete product");
        } catch (ProductItemDAOException e) {
            new ProductItemDAOException("Cannot delete product\"");
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
            ProductDAOImpl ProductDAO = factory.getDAO(ProductDAOImpl.class);
            ProductItemDAOImpl itemDAO = factory.getDAO(ProductItemDAOImpl.class);
            ProductItem paramItem = new ProductItem(product, amount);
            Product baseProduct = findById(product.getId());
            int baseQuantity = itemDAO.findAmountByProductWarehouse(product.getId());
            ProductItem baseItem = new ProductItem(baseProduct, baseQuantity);
            if (paramItem.equals(baseItem)) {
                return false;
            }
            factory.startTransaction();
            int affectedRowsProduct = ProductDAO.updateProduct(product);
            int affectedRowsWarehouse = itemDAO.updateAmountInWarehouse(amount, product.getId());
            factory.commitTransaction();
            result = !(affectedRowsProduct == 0 && affectedRowsWarehouse == 0);
            return result;
        } catch (DAOException e) {
            try {
                factory.rollbackTransaction();
                Logger.info("transaction rollback done");
            } catch (DAOException e1) {
                Logger.error("cannot rollback done");
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
    public List<Product> getSubcatalogProduct(int id) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            ProductDAOImpl ProductDAO = factory.getDAO(ProductDAOImpl.class);
            return ProductDAO.getSubcatalogProduct(id);
        } catch (DAOException e) {
            Logger.error("Cannot get subcategory by product");
            e.printStackTrace();
            throw new ServiceException("Cannot get subcategory by product");
        } catch (ProductDAOException e) {
            Logger.error("Cannot get subcategory by product");
            e.printStackTrace();
            throw new ServiceException("Cannot get subcategory by product");
        }
    }

    @Override
    public boolean updateBasketItem(Integer cartItemId, Integer amount) throws ServiceException {
        try {
            AbstractDAOFactory factory = new DAOFactoryImpl();
            ProductItemDAOImpl productDAO = factory.getDAO(ProductItemDAOImpl.class);
            return productDAO.updateBasketItemById(cartItemId, amount);
        } catch (DAOException e) {
            Logger.error("Cannot get subcategory by Product");
            e.printStackTrace();
            throw new ServiceException("Cannot get subcategory by Product");
        } catch (ProductItemDAOException e) {
            Logger.error("Cannot get subcategory by Product");
            e.printStackTrace();
            throw new ServiceException("Cannot get subcategory by Product");
        }
    }
}
