package com.mu.common;

import com.alibaba.fastjson.JSON;
import com.mu.common.entity.Mail;
import com.mu.common.entity.User;
import com.mu.common.service.MsgLogService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 消息队列测试类
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserTest {

    @Autowired
    private MsgLogService msgLogService;

    @Resource
    private RedisTemplate redisTemplate;


    @Test
    public void test1() {
        System.out.println("---------第一次-------");
        System.out.println(this.msgLogService.getMsgId("82820608-4ff1-4523-ba37-687227c6f99f"));
        System.out.println("---------第二次-------");
        System.out.println(this.msgLogService.getMsgId("82820608-4ff1-4523-ba37-687227c6f99f"));
    }


    @Test
    public void testSet() {
        redisTemplate.opsForValue().set("key", "张三丰");
    }

    @Test
    public void testGet() {
        System.out.println(this.redisTemplate.opsForValue().get("key"));
    }

    @Test
    public void testUserUseJson() {
        User user = new User();
        user.setPassword("123456");
        user.setName("王晓伟");
        user.setMail(new Mail());
        this.redisTemplate.opsForValue().set("key1", JSON.toJSONString(user));
    }

}
