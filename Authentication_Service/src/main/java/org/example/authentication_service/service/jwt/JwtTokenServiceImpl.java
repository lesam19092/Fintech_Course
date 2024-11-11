package main.java.org.example.authentication_service.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import main.java.org.example.authentication_service.service.user.UserService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Data
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {


    private String secret;

    private final TokenService tokenService;

    private final UserService userService;

    @Override
    public String generateToken(UserDetails userDetails, long minutes) {
        Map<String, Object> claims = createClaims(userDetails);
        Date issuedDate = new Date();
        Date expiredDate = calculateExpirationDate(issuedDate, minutes);

        User user = userService.findByUsername(userDetails.getUsername());
        String jwtToken = buildJwtToken(userDetails, claims, issuedDate, expiredDate);

        tokenService.revokeAllTokenByUser(user);
        tokenService.saveToken(user, jwtToken);

        return jwtToken;
    }


    @Override
    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    @Override
    public boolean isValid(String token) {
        return tokenService.isValid(token);
    }

    @Override
    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Map<String, Object> createClaims(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);
        return claims;
    }

    private Date calculateExpirationDate(Date issuedDate, long minutes) {
        return new Date(issuedDate.getTime() + minutes * 60 * 1000);
    }

    private String buildJwtToken(UserDetails userDetails, Map<String, Object> claims, Date issuedDate, Date expiredDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}