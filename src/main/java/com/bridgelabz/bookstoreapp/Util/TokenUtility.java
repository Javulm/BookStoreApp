package com.bridgelabz.bookstoreapp.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

@Component
public class TokenUtility {
    private static final String SECRET="Javul";
    public String createToken(int Id)
    {
        String token= JWT.create().withClaim("id",Id).sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    public int decodeJWT(String jwt)
    {
        int id=0;
        if(jwt!=null)
        {
            id=JWT.require(Algorithm.HMAC256(SECRET)).build().verify(jwt).getClaim("id").asInt();
        }
        return id;
    }
}
