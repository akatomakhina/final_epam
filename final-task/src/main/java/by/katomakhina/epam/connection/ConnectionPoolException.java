package by.katomakhina.epam.connection;

public class ConnectionPoolException extends Exception {

    private static final long serialVersionUID = 5186966254692357655L;

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
