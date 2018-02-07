package by.katomakhina.epam.connection;

import java.util.ResourceBundle;

public final class ConstantConnection {
    private static final String FILE_PATH = "database";

    private static final String URL_NAME = "URL";
    private static final String USERNAME_NAME = "USERNAME";
    private static final String PASSWORD_NAME = "PASSWORD";
    private static final String LIMIT_NAME = "CONNECTION_LIMIT";

    public static final String URL;
    public static final String USERNAME;
    public static final String PASSWORD;
    public static final Integer CONNECTION_LIMIT;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle(FILE_PATH);
        URL = bundle.getString(URL_NAME);
        USERNAME = bundle.getString(USERNAME_NAME);
        PASSWORD = bundle.getString(PASSWORD_NAME);
        CONNECTION_LIMIT = Integer.parseInt(bundle.getString(LIMIT_NAME));
    }

    private ConstantConnection() {
    }
}