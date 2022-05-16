package uz.pdp.hrmanagementsystem.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagementsystem.dto.UserLoginModel;
import uz.pdp.hrmanagementsystem.dto.UserModel;
import uz.pdp.hrmanagementsystem.entities.enums.JwtClaims;
import uz.pdp.hrmanagementsystem.exceptions.CommonJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class JwtUtil {

    private final String key = "hrmsystem";

    @Value("600000")
    private final Long expireDateMilliseconds = 600000000L;

    public String generateToken(Map<String, Object> claims, UserLoginModel userModel) {
        return Jwts
                .builder()
                .setClaims(claims)
                .claim(JwtClaims.EMAIL.toString(), userModel.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireDateMilliseconds))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("HRMS jwt exception", e);
            e.printStackTrace();
            throw new CommonJwtException("Error occured while parsing jwt token" + e.getMessage());
        }
    }

    public String extractEmail(String token) {
        return parseToken(token).get("email").toString();
    }

    public Boolean isTokenExpired(String token) {
        Claims claims = parseToken(token);
        Date expiration = claims.getExpiration();
        if (!expiration.before(new Date(System.currentTimeMillis()))) {
            throw new CommonJwtException("Jwt token expired, expire Date: " + expiration);
        }
        return true;
    }



}
