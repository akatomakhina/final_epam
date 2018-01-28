package by.katomakhina.epam.controller.action.exception;

public class ActionException extends Exception{
    private static final long serialVersionUID = -6901962161364726614L;

    public ActionException() {
        super();
    }

    public ActionException(String message) {
        super(message);
    }

    public ActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionException(Throwable cause) {
        super(cause);
    }
}
