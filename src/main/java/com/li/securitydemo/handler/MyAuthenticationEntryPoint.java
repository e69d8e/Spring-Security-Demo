package com.li.securitydemo.handler;

import com.alibaba.fastjson2.JSON;
import com.li.securitydemo.entity.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Object json = JSON.toJSON(Result.error("服务器异常"));
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(500);
        response.getWriter().write(json.toString());
    }
}
