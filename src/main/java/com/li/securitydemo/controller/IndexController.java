package com.li.securitydemo.controller;

import com.li.securitydemo.entity.Result;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {
    @GetMapping("/")
    public Result index() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println(securityContext.getAuthentication().getPrincipal()); // 获取用户信息
        System.out.println(securityContext.getAuthentication().getAuthorities()); // 获取用户权限
        System.out.println(securityContext.getAuthentication().getCredentials()); // 获取用户凭证
        Map<String, Object> map = new HashMap<>();
        map.put("principal", securityContext.getAuthentication().getPrincipal());
        map.put("authorities", securityContext.getAuthentication().getAuthorities());
        map.put("credentials", securityContext.getAuthentication().getCredentials());
        return Result.ok(map);
    }

}
