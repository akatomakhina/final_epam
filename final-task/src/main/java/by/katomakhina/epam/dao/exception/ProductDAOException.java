package by.katomakhina.epam.dao.exception;

public class ProductDAOException extends Exception {
    public ProductDAOException() {
        super();
    }

    public ProductDAOException(String message) {
        super(message);
    }

    public ProductDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductDAOException(Throwable cause) {
        super(cause);
    }
}
