package com.mu.common.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送消息者
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息到消息队列
     *
     * @param msg 向消息队列发送的消息
     */
    public void sender(String msg) {
        //  调用向消息队列的方法
        //        参数一： 向哪一个消息队列发送消息(匹配消息对列的名称)   匹配规则
        //       参数二：发送的什么消息（消息）
        amqpTemplate.convertAndSend("hello-queue", msg);
    }
}
