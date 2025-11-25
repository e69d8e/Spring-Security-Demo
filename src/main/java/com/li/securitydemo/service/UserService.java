package com.li.securitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.securitydemo.dto.UserDTO;
import com.li.securitydemo.entity.Result;
import com.li.securitydemo.entity.User;

public interface UserService extends IService<User> {
    Result register(UserDTO userDTO);
}
