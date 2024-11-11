package main.java.org.example.authentication_service.service.token;



public interface TokenService {

    Token saveToken(User user, String token);

    void revokeAllTokenByUser(User user);

    boolean isValid(String token);

}