package com.mu.common;


import com.mu.common.rabbitmq.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 消息队列测试类
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class QueueTest {

    @Autowired
    private Sender sender;

    @Test
    public void test1() {
        this.sender.sender("RabbitMQ");
    }
}
