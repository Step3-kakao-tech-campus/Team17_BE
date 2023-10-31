package com.kakaoseventeen.dogwalking._core.security.handler;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");
        MessageCode errorCode;

        log.debug("log: exception: {} ", exception);


         //토큰 없는 경우


        if(exception == null) {
            errorCode = MessageCode.NON_LOGIN;
            setResponse(response, errorCode);
            return;
        }


         // 토큰 만료된 경우


        if(exception.equals(MessageCode.EXPIRED_TOKEN.getValue())) {
            errorCode = MessageCode.EXPIRED_TOKEN;
            setResponse(response, errorCode);
            return;
        }


         //토큰 시그니처가 다른 경우


        if(exception.equals(MessageCode.INVALID_TOKEN.getValue())) {
            errorCode = MessageCode.INVALID_TOKEN;
            setResponse(response, errorCode);
        }
    }



    private void setResponse(HttpServletResponse resp, MessageCode errorCode) throws IOException {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().println("{ \"success\": false, \"response\": null, \"error\": { \"message\": \"" +
                errorCode.getValue() + "\", \"status\": \"" + HttpStatus.UNAUTHORIZED  + "\" } }");
    }

}
