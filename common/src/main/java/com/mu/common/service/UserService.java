package com.mu.common.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description 用户服务类
 * @Author by mujiangkui
 * @Date 2021/1/24 23:16
 */
@Service
public interface UserService extends UserDetailsService {
}
