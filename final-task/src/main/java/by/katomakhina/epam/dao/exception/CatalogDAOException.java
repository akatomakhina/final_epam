package by.katomakhina.epam.dao.exception;

public class CatalogDAOException extends Exception{
    public CatalogDAOException() {
        super();
    }

    public CatalogDAOException(String message) {
        super(message);
    }

    public CatalogDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CatalogDAOException(Throwable cause) {
        super(cause);
    }
}
