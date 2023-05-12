package com.sv.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * token manager
 * </p>
 *
 * @author
 * @since
 */
@Component
public class TokenManager {

    private long tokenExpiration = 24*60*60*1000;
    private String tokenSignKey = "123456";

    // use JWT to create token
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username) // put username in token
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) // token expired after a day
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact(); // encode
        return token;
    }

    // get user info from token
    public String getUserFromToken(String token) {
        String user = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject(); // get user info
        return user;
    }

    public void removeToken(String token) {
        // jwtToken doesn't need to delete token
    }

}
