package com.li.securitydemo.config;

import com.alibaba.fastjson2.JSON;
import com.li.securitydemo.entity.Result;
import jakarta.servlet.ServletException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        Object json = JSON.toJSON(Result.error("用户已从其他设备登录"));
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().setStatus(402);
        event.getResponse().getWriter().write(json.toString());
    }
}
