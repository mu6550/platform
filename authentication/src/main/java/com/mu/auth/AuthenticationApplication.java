package com.mu.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * AuthenticationApplication class
 *
 * @author 穆江魁
 * @date 2021/04/13
 */
@EnableEurekaClient
@SpringBootApplication
public class AuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
        System.out.println("权限项目启动完成");
    }
}
