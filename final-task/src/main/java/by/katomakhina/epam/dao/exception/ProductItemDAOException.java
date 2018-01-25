package by.katomakhina.epam.dao.exception;

public class ProductItemDAOException extends Exception{
    public ProductItemDAOException() {
        super();
    }

    public ProductItemDAOException(String message) {
        super(message);
    }

    public ProductItemDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductItemDAOException(Throwable cause) {
        super(cause);
    }
}
