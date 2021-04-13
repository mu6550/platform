package com.mu.common.service.impl;

import com.mu.common.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @ClassName UserServiceImpl
 * @Description TDD
 * @Author by mujiangkui
 * @Date 2021/1/24 23:17
 */
public class UserServiceImpl implements UserService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
