package util;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

public class JWTTest {



    @Test
    public void t1(){
        SecretKey key = Jwts.SIG.HS512.key().build();
        System.out.println(key.toString());
    }

    @Test
    public void t2(){
        Map m = new HashMap();
        m.put("a","a");

        SecretKey secretKey = Jwts.SIG.HS512.key().build();
        String token = Jwts.builder()
                .claims(m)
                .signWith(secretKey)
                .compact();
        System.out.println("token:"+token);
        Jwt jwt = Jwts.parser()
//                .clockSkewSeconds(480)
                .verifyWith(secretKey)
                .build()
                .parse(token);
        if (jwt instanceof Jwe<?>) {
            Jwe<?> jwe = (Jwe<?>)jwt;
            if (jwe.getPayload() instanceof Claims) {
                Jwe<Claims> claimsJwe = (Jwe<Claims>)jwe;
                Claims payload = claimsJwe.getPayload();
                System.out.println(JSON.toJSONString(payload));
            }
        }
    }
}
