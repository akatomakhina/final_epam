package by.katomakhina.epam.dao.exception;

public class OrderDAOException extends Exception{

    private static final long serialVersionUID = 2650060533381244237L;

    public OrderDAOException() {
        super();
    }

    public OrderDAOException(String message) {
        super(message);
    }

    public OrderDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDAOException(Throwable cause) {
        super(cause);
    }
}
