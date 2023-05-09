package learn.bec.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import learn.bec.models.MemberUser;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;


@Component
public class JwtConverter {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String ISSUER = "recipes-API";
    private static final int EXPIRATION_MINUTES = 15;
    private static final int EXPIRATION_MILLISECONDS = EXPIRATION_MINUTES * 60 * 1000;

    public String userToToken(MemberUser user) {

        List<String> authorities = user.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList();

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getUsername())
                .claim("memberUserId", user.getMemberUserId())
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLISECONDS))
                .signWith(key)
                .compact();
    }

    public MemberUser tokenToUser(String token) { // Bearer eyJu...

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));

            String username = jws.getBody().getSubject();
            int appUserId = jws.getBody().get("memberUserId", Integer.class);
            List<String> roles = jws.getBody().get("authorities", List.class);

            MemberUser user = new MemberUser( );
            user.setMemberUserId(appUserId);
            user.setUsername(username);
            user.setPassword("");
            user.addAuthorities(roles);
            return user;

        } catch (JwtException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}
