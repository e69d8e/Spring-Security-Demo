package com.li.securitydemo.controller;

import com.li.securitydemo.dto.UserDTO;
import com.li.securitydemo.entity.Result;
import com.li.securitydemo.entity.User;
import com.li.securitydemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "用户接口")
public class UserController {

    private final UserService userService;

    @Operation(summary = "用户列表", description = "查询用户列表")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/list")
    public Result list() {
        List<User> list = userService.list();
        return Result.ok(list, (long) list.size());
    }

    @Operation(summary = "用户注册", description = "用户注册")
//    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/authTest1")
    public String authTest1() {
        return "authTest1";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authTest2")
    public String authTest2() {
        return "authTest2";
    }
}
