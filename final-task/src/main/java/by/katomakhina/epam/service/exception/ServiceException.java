package by.katomakhina.epam.service.exception;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 2124999825439375699L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
