package by.katomakhina.epam.dao.exception;

public class CatalogDAOException extends Exception{

    private static final long serialVersionUID = -6456339004996607523L;

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
