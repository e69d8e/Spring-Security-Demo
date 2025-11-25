package com.li.securitydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.li.securitydemo.config.DBUserDetailsManager;
import com.li.securitydemo.dto.UserDTO;
import com.li.securitydemo.entity.Result;
import com.li.securitydemo.entity.User;
import com.li.securitydemo.mapper.UserMapper;
import com.li.securitydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final DBUserDetailsManager dbUserDetailsManager;
    @Override
    public Result register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        String password = userDTO.getPassword();
        // 工作因子，默认值是10，最小值是4，最大值是31，值越大密码生成速度越慢
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode(password);
        user.setPassword("{bcrypt}" + result);
        user.setEnabled(true);
        save(user);
        return Result.ok();

        /*UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withDefaultPasswordEncoder()
                .username(userDTO.getUsername()) //自定义用户名
                .password(userDTO.getPassword()) //自定义密码
                .build();
        dbUserDetailsManager.createUser(userDetails);
        return Result.ok();*/
    }
}
