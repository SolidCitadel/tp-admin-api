package solidcitadel.tp.admin.api.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import solidcitadel.tp.admin.api.user.UserRoleEnum;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";  // 쿠키 이름
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    public static final String AUTHORIZATION_KEY = "auth";  // 권한
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.access.token.expiration.ms}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh.token.expiration.ms}")
    private long refreshTokenExpiration;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static final Logger logger = LoggerFactory.getLogger("JWT 로그");

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createAccessToken(String username, UserRoleEnum role) {
        Date date = new Date();
        Date expiresAt = new Date(date.getTime() + accessTokenExpiration);
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(expiresAt)
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createRefreshToken(String username) {
        Date date = new Date();
        Date expiresAt = new Date(date.getTime() + refreshTokenExpiration);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiresAt)
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public void addAccessTokenToCookie(String token, HttpServletResponse res) {
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        ResponseCookie cookie = ResponseCookie.from(AUTHORIZATION_HEADER, encodedToken)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        res.addHeader("Set-Cookie", cookie.toString());
    }

    public void addRefreshTokenToCookie(String token, HttpServletResponse res) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_HEADER, token)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        res.addHeader("Set-Cookie", cookie.toString());
    }

    public String getAccessTokenFromCookie(HttpServletRequest req) {
        return getTokenFromCookie(req, AUTHORIZATION_HEADER);
    }

    public String getRefreshTokenFromCookie(HttpServletRequest req) {
        return getTokenFromCookie(req, REFRESH_TOKEN_HEADER);
    }

    public String substringToken(String tokenValue){
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(BEARER_PREFIX.length());
        }
        logger.error("Not a valid token");
        throw new NullPointerException("Not a valid token");
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e){
            logger.error("Invalid JWT signature");
        } catch (ExpiredJwtException e){
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException e){
            logger.error("JWT claims is empty");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private String getTokenFromCookie(HttpServletRequest req, String cookieName) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals(cookieName))
                    return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
        return null;
    }

    public void deleteCookie(HttpServletResponse res, String cookieName) {
        ResponseCookie cookie = ResponseCookie.from(cookieName, "")
                .path("/")
                .maxAge(0)
                .build();
        res.addHeader("Set-Cookie", cookie.toString());
    }

    public void deleteAccessTokenFromCookie(HttpServletResponse res) {
        deleteCookie(res, AUTHORIZATION_HEADER);
    }
}
