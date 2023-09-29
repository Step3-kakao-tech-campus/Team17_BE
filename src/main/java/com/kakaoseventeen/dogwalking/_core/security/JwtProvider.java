package com.kakaoseventeen.dogwalking._core.security;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class JwtProvider {

    private static final String PREFIX = "Bearer ";
    private static final String SECRET = "thisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkey";
    public static final Long accessTokenValidTime = 1000L * 60 * 30;
    public static final Long refreshTokenValidTime = 1000L * 60 * 60 * 24 *7;


    public static Key createKey(){
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
        return signingKey;
    }
}
