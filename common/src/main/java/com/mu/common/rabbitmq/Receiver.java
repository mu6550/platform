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
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 消息的接收者
 */

@Component
public class Receiver {

    private final static Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private MsgLogService msgLogService;

    @Resource
    private JavaMailSender mailSender;

    /**
     * 接收消息的方法，采用的RabbitMQ的监听机制方式
     *
     * @param message message
     * @param channel channel
     */

    @RabbitListener(queues = QueueConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        Mail mail = MessageHelper.msgToObj(message, Mail.class);
        LOGGER.info("received message: {}", mail.toString());
        String msgId = mail.getMsgId();
        MsgLog msgLog = msgLogService.getMsgId(msgId);
        if (null == msgLog || msgLog.getStatus().equals(3)) {
            LOGGER.info("repeat message, msgId : {}", mail.getMsgId());
            return;
        }
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            SimpleMailMessage mailMessage = MailUtil.send(mail);
            mailSender.send(mailMessage);
            msgLogService.updateStatus(msgId, 3);
            // 手动设置Ack为false
            channel.basicAck(deliveryTag, false);
        } catch (MailException ex) {
            channel.basicNack(deliveryTag, false, true);
        }

    }
}
