package com.kakaoseventeen.dogwalking._core.security;

import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    private static final String PREFIX = "Bearer ";
    private static final String SECRET = "thisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkey";
    public static final Long accessTokenValidTime = 1000L * 60 * 30; //30분
    public static final Long refreshTokenValidTime = 1000L * 60 * 60 * 24 *7; //7일
}
