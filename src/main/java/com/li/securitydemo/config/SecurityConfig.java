package com.li.securitydemo.config;

import com.alibaba.fastjson2.JSON;
import com.li.securitydemo.entity.Result;
import com.li.securitydemo.handler.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity // 开启方法权限控制
public class SecurityConfig {

    // 内存中保存用户信息
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("user") // 用户名
//                        .password("password") // 密码
//                        .roles("USER") // 角色
//                        .build());
//        return manager;
//    }

    // 内存中保存用户信息
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{noop}password") // 或使用加密密码
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //authorizeRequests()：开启授权保护
        //anyRequest()：对所有请求开启授权保护
        //authenticated()：已认证请求会自动被授权
        http.authorizeHttpRequests(
                authorize ->
                        authorize
                                // 拥有权限 ROLE_ADMIN 和 ROLE_USER 可以访问 /user/authTest1 有任意一条权限即可访问
//                                .requestMatchers("/user/authTest1").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                // 拥有权限 ROLE_ADMIN 和 ROLE_TEST 可以访问 /user/authTest2
//                                .requestMatchers("/user/authTest2").hasAnyAuthority("ROLE_ADMIN", "ROLE_TEST")
                                // 角色 ROLE_USER 可以访问 /user/authTest1 必须要是 USER 角色才能访问
//                                .requestMatchers("/user/authTest1").hasRole("USER")
                                // 角色 ROLE_USER 可以访问 /user/authTest1 必须要是 ADMIN 角色才能访问
//                                .requestMatchers("/user/authTest2").hasRole("ADMIN")
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/doc.html",
                                        "/webjars/**",
                                        "user/register"
                                ).permitAll() // 放行swagger
                                .anyRequest() // 所有请求
                                .authenticated()) // 已认证请求会自动被授权
                .formLogin(
                        from -> from.loginPage("/login").permitAll()
                                .successHandler(new MyAuthenticationSuccessHandler()) // 登录成功处理
                                .failureHandler(new MyAuthenticationFailHandler()) // 登录失败处理
                )// 自定义登录页面 .permitAll() 表示登录页面可以任意访问
//                .formLogin(Customizer.withDefaults()); // 表单登录方式 默认登录页面
//                .httpBasic(Customizer.withDefaults()); // 基本授权方式 浏览器弹出框输入用户名密码
        ;
        // 注销
        http.logout(logout -> logout.logoutSuccessHandler(new MyLogoutHandler()));
        // 错误信息
        http.exceptionHandling(
                exception -> {
                    exception.authenticationEntryPoint(new MyAuthenticationEntryPoint());

                    // 匿名内部类写法
                    /*exception.accessDeniedHandler(
                            new AccessDeniedHandler() {
                                @Override
                                public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                    Object json = JSON.toJSON(Result.error("该用户没有权限"));
                                    response.setContentType("application/json;charset=UTF-8");
                                    response.setStatus(403);
                                    response.getWriter().write(json.toString());
                                }
                            }
                    ); */

                    // lambda表达式写法
                    /*exception.accessDeniedHandler(
                            (request, response, accessDeniedException) -> {
                                Object json = JSON.toJSON(Result.error("该用户没有权限"));
                                response.setContentType("application/json;charset=UTF-8");
                                response.setStatus(403);
                                response.getWriter().write(json.toString());
                            }
                    ); */
                    exception.accessDeniedHandler(new MyAccessDeniedHandler());// 拒绝访问处理 用户没有访问权限的时候
                }

        );
        // 跨域
        http.cors(withDefaults());
        // 关闭csrf攻击防御
        http.csrf(AbstractHttpConfigurer::disable);
        // 最大会话数 1
        http.sessionManagement(session ->
                session.maximumSessions(1) // 最大会话数 1
                        .expiredSessionStrategy(new MySessionInformationExpiredStrategy())); // 会话过期处理
        return http.build();
    }

}
