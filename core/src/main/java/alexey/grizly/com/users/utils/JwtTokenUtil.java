package alexey.grizly.com.users.utils;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;



@Component
public class JwtTokenUtil {


    public String generateToken(
            Collection<? extends GrantedAuthority> authorities, String email, Date issuedDate, Date expiredDate,Long id) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authoritiesList = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("authorities", authoritiesList);
        claims.put("id",id);
        return
        Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, getSecret())
                .compact();
    }

    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public List<String> getAuthorities(String token) {
        return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("authorities", List.class));
    }
    public String generateRefreshTokenFromEmail(String email) {
        return UUID.nameUUIDFromBytes(email.getBytes()).toString();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] getSecret() {
        return "h4f8093h4f983yhrt9834hr0934hf0hf493g493gf438rh438th34g34g".getBytes();
    }


}
