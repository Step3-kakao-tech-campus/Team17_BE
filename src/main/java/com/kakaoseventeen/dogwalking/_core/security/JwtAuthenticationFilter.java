package com.kakaoseventeen.dogwalking._core.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider){
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    /**
     * 헤더에서 토큰을 추출하는 메서드
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if(token == null){
            chain.doFilter(request, response);
            return;
        }
        try{
            String extractedToken = token.replace("Bearer ", "");
            if (jwtProvider.isTokenValidate(extractedToken)) {
                Authentication authentication = jwtProvider.getAuthentication(extractedToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (final JwtException e) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
            response.getWriter().println("{ \"success\": false, \"response\": null, \"error\": { \"message\": \"" +
                    e.getMessage() + "\", \"status\": \"" + HttpStatus.UNAUTHORIZED  + "\" } }");
    	}

    }

}
