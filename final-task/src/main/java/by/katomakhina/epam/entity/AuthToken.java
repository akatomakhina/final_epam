package by.katomakhina.epam.entity;

import java.io.Serializable;

public final class AuthToken implements Serializable {
    private String accessToken;
    private String refreshToken;

    public AuthToken() {
    }

    public AuthToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        if (accessToken != null ? !accessToken.equals(authToken.accessToken) : authToken.accessToken != null)
            return false;
        return refreshToken != null ? refreshToken.equals(authToken.refreshToken) : authToken.refreshToken == null;
        /*return Objects.equals(accessToken, authToken.accessToken) &&
                Objects.equals(refreshToken, authToken.refreshToken);*/
    }

    @Override
    public int hashCode() {
        int result = accessToken != null ? accessToken.hashCode() : 0;
        result = 31 * result + (refreshToken != null ? refreshToken.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "AuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
