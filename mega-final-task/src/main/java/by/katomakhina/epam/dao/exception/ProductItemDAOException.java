package by.katomakhina.epam.dao.exception;

public class ProductItemDAOException extends Exception{

    private static final long serialVersionUID = -1642750812377582119L;

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
