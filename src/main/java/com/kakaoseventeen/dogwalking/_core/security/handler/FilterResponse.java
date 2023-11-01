package com.kakaoseventeen.dogwalking._core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaoseventeen.dogwalking._core.utils.exception.SecurityFilterException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class FilterResponse {
    public static void unAuthorized(HttpServletResponse resp, SecurityFilterException e) throws IOException {
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setContentType("application/json; charset=utf-8");
     //   ObjectMapper om = new ObjectMapper();
     //   String responseBody = om.writeValueAsString(e.message);
        resp.getWriter().println("{ \"success\": false, \"response\": null, \"error\": { \"message\": \"" +
                e.message + "\", \"status\": \"" + HttpStatus.UNAUTHORIZED  + "\" } }");
    }

    public static void forbidden(HttpServletResponse resp, SecurityFilterException e) throws IOException {
        resp.setStatus(HttpStatus.FORBIDDEN.value());
        resp.setContentType("application/json; charset=utf-8");
      //  ObjectMapper om = new ObjectMapper();
       // String responseBody = om.writeValueAsString(e.message);
        resp.getWriter().println("{ \"success\": false, \"response\": null, \"error\": { \"message\": \"" +
                e.message + "\", \"status\": \"" + HttpStatus.FORBIDDEN  + "\" } }");
    }
}
