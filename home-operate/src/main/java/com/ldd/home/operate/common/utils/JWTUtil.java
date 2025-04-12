package com.ldd.home.operate.common.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Map;

public class JWTUtil {

    /**
     * generate HS512 signature secret key
     * @return
     */
    public static SecretKey HS512secretKey(){
        return Jwts.SIG.HS512.key().build();
    }

    /**
     * generate token
     * @param claims
     * @param secretKey
     * @return
     */
    public static String token(Map claims,SecretKey secretKey){
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

    /**
     * resolve token
     * @return
     */
    public static Map parseToken(String token,SecretKey secretKey){
        Jwt jwt = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token);
        if(jwt instanceof Map){
            return (Map) jwt;
        }else{
            throw new JwtException("token resolve exception!");
        }
    }
}
