package by.katomakhina.epam.dao.exception;

public class ProductDAOException extends Exception {

    private static final long serialVersionUID = -12613757702630651L;

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
