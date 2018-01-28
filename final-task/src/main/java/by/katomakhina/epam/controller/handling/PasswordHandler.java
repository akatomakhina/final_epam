package by.katomakhina.epam.controller.handling;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHandler {
    public String getHashedPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot hash password");

        }
    }
}
