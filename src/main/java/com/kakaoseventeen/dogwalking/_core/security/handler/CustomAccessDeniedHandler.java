package com.kakaoseventeen.dogwalking._core.security.handler;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().println("{ \"success\": false, \"response\": null, \"error\": { \"message\": \"" +
                MessageCode.ACCESS_DENIED + "\", \"status\": \"" + HttpStatus.FORBIDDEN  + "\" } }");
    }
}
