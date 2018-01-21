package by.katomakhina.epam.dao.exception;

public class UserDAOException extends DAOException {

    private static final long serialVersionUID = -6782069451457016867L;

    public UserDAOException() {
        super();
    }

    public UserDAOException(String message) {
        super(message);
    }

    public UserDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDAOException(Throwable cause) {
        super(cause);
    }
}
