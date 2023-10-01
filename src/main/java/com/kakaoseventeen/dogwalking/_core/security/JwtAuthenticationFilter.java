package com.kakaoseventeen.dogwalking._core.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 헤더에서 JWT 받아오기
        String token = request.getHeader("Authorization");

        if(token == null){
            chain.doFilter(request, response);
            return;
        }
        String extractedToken = token.replace("Bearer ", "");
        // 유효한 토큰인지 확인
        if (jwtProvider.isTokenValidate(extractedToken)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아온다 -> Authentication 객체에 저장
            Authentication authentication = jwtProvider.getAuthentication(extractedToken);
            // SecurityContext 에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }


}
