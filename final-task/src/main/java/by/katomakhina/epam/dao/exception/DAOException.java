package by.katomakhina.epam.dao.exception;

public class DAOException extends Exception {

    private static final long serialVersionUID = 824334027442950789L;

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
