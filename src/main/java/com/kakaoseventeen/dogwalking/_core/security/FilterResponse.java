package com.kakaoseventeen.dogwalking._core.security;

import com.kakaoseventeen.dogwalking._core.utils.exception.member.SecurityFilterException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * 필터단에서 예외가 발생했을 때, 클라이언트에게 응답을 보내는 클래스
 *
 * @author 곽민주
 * @version 1.0
 */
public class FilterResponse {
    public static void unAuthorized(HttpServletResponse resp, SecurityFilterException e) throws IOException {
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().println("{ \"success\": false, \"response\": null, \"error\": { \"message\": \"" +
                e.message + "\", \"status\": \"" + HttpStatus.UNAUTHORIZED  + "\" } }");
    }

    public static void forbidden(HttpServletResponse resp, SecurityFilterException e) throws IOException {
        resp.setStatus(HttpStatus.FORBIDDEN.value());
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().println("{ \"success\": false, \"response\": null, \"error\": { \"message\": \"" +
                e.message + "\", \"status\": \"" + HttpStatus.FORBIDDEN  + "\" } }");
    }
}
