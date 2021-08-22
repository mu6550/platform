package com.mu.common.service.impl;

import com.mu.common.entity.Mail;
import com.mu.common.entity.MsgLog;
import com.mu.common.interceptor.MyInterceptor;
import com.mu.common.rabbitmq.QueueConfig;
import com.mu.common.service.MailService;
import com.mu.common.service.MsgLogService;
import com.mu.common.util.R;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MailServiceImpl implements MailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyInterceptor.class);

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public R sendMail(Mail mail) {
        try {
            checkMail(mail);
            saveMsg(mail);
            return R.ok().message("邮件发送成功");
        } catch (RuntimeException e) {
            LOGGER.error("发送失败", e);
            return R.error().code(500).message("邮件发送失败" + e.getMessage());
        }
    }

    /**
     * 获取发件人信息
     */
    @Override
    public String getMailSendFrom() {
        return mailSender.getJavaMailProperties().getProperty("from");
    }

    private void checkMail(Mail mail) {
        if (StringUtils.isEmpty(mail.getTo())) {
            LOGGER.error("邮件收信人不能为空");
            throw new RuntimeException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(mail.getTitle())) {
            LOGGER.error("邮件主题不能为空");
            throw new RuntimeException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(mail.getText())) {
            LOGGER.error("邮件内容不能为空");
            throw new RuntimeException("邮件内容不能为空");

        }
    }

    @Override
    public R saveMsg(Mail mail) {
        String msgId = UUID.randomUUID().toString();
        mail.setMsgId(msgId);
        MsgLog msgLog = new MsgLog(msgId, mail, QueueConfig.MAIL_EXCHANGE_NAME, QueueConfig.MAIL_ROUTING_KEY_NAME);
        msgLogService.insertMsgLog(msgLog);  // 消息入库
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(QueueConfig.MAIL_EXCHANGE_NAME, QueueConfig.MAIL_ROUTING_KEY_NAME,
                mail, correlationData);
        LOGGER.info("--------------发送消息给rabbitmq-------------------");
        return R.ok();
    }

}
