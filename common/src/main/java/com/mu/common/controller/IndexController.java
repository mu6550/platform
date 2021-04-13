package com.mu.common.controller;

import com.mu.common.entity.Mail;
import com.mu.common.entity.User;
import com.mu.common.exception.HandleBusinessException;
import com.mu.common.util.R;
import com.mu.common.util.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 主页面类
 *
 * @Author mjk
 * @date 21:10 2021/1/10
 */

@RestController
@RequestMapping(value = "/index")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @PostMapping(value = "/test")
    public R test(User user) {
        logger.info("name：{}", user.getName());
        logger.info("pass：{}", user.getPassword());
        return R.ok();
    }

    @PostMapping(value = "/null")
    public R nullPoint(@RequestBody User user) {
        logger.info(user.toString());
        if (!"".equals(user.getName())) {
            return R.ok();
        }
        logger.info("不是空指针");
        return R.ok();
    }

    @GetMapping(value = "/getUserInfo")
    public R getUserInfo() {
        Map<String, Object> map = new HashMap<>(10);
        User user = new User();
        Mail mail = new Mail();
        user.setMail(mail);
        user.setName("王小明");
        user.setPassword("e123");
        map.put("user", user);
        return R.ok().data(map).message("获取用户个人信息");
    }

    @GetMapping(value = "/getList")
    public R select() {
        int i = 0;
        try {
            int result = 1 / i;
        } catch (Exception ex) {
            throw new HandleBusinessException(ResultCodeEnum.UNEXPECTED_EXCEPTION);
        }
        return R.ok();
    }
}
