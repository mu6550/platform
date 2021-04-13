package com.mu.common.rabbitmq;

import com.mu.common.entity.Mail;
import com.mu.common.entity.MsgLog;
import com.mu.common.service.MsgLogService;
import com.mu.common.util.MailUtil;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消息的接收者
 */

@Component
public class Receiver {

    private final static Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private MailUtil mailUtil;

    /**
     * 接收消息的方法，采用的RabbitMQ的监听机制方式
     *
     * @param message
     * @param channel
     */

    @RabbitListener(queues = QueueConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        Mail mail = MessageHelper.msgToObj(message, Mail.class);
        LOGGER.info("收到消息: {}", mail.toString());
        String msgId = mail.getMsgId();
        MsgLog msgLog = msgLogService.getMsgId(msgId);
        if (null == msgLog || msgLog.getStatus().equals(3)) {
            LOGGER.info("重复消息,msgId : {}", mail.getMsgId());
            return;
        }

        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();

        boolean send = mailUtil.send(mail);
        if (send) {
            msgLogService.updateStatus(msgId, 3);
            channel.basicAck(deliveryTag, false);
        } else {
            channel.basicNack(deliveryTag, false, true);
        }

    }
}
