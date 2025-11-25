package com.li.securitydemo.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.li.securitydemo.entity.Authority;
import com.li.securitydemo.entity.User;
import com.li.securitydemo.mapper.AuthorityMapper;
import com.li.securitydemo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


// 数据库中保存用户信息
@Slf4j
@Component
@RequiredArgsConstructor
public class DBUserDetailsManager implements UserDetailsService {

    private final UserMapper userMapper;
    private final AuthorityMapper authorityMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户 " + username + " 不存在");
        } else {
            // 添加权限
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            // 将数据库权限表的数据添加到权限列表中
            Authority authority = authorityMapper.selectById((Serializable) user.getAuthorityId());
            /*if (authority != null && authority.getAuthority() != null) {
                authorities.add((GrantedAuthority) authority::getAuthority);
                log.info("权限：{}", authority.getAuthority());
            }
            // 构建 UserDetail 对象 方式一
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), // 自定义用户名
                    user.getPassword(), // 自定义密码
                    user.getEnabled(), // 用户账号是否未禁用
                    true, // 用户账号是否未过期
                    true, // 用户凭证是否未过期
                    true, // 用户是否未被锁定
                    authorities); // 添加权限列表*/
            // 构建 UserDetail 对象 方式二
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()) //自定义用户名
                    .password(user.getPassword()) //自定义密码
                    .disabled(!user.getEnabled()) // 用户账号是否禁用
                    .accountExpired(!user.getEnabled()) // 用户凭证是否过期
                    .accountLocked(!user.getEnabled()) // 用户是否被锁定
                    .roles(authority.getAuthority()) // 添加角色
//                    .authorities("") // 添加权限 会覆盖前面的 roles(authority.getAuthority())
                    .build();
        }

    }

    // implements UserDetailsPasswordService
/*    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }*/

    // implements UserDetailsManager
   /* @Override
    public void createUser(UserDetails userDetails) {
        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEnabled(true);
        userMapper.insert(user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }*/
}
