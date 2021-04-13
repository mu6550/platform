package com.mu.common.util;

import com.mu.common.entity.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Component
public class MailUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender mailSender;

    public boolean send(Mail mail) {
        SimpleMailMessage mailMessage = null;
        try {
            mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(mail.getFrom());
            mailMessage.setTo(mail.getTo());
            mailMessage.setSubject(mail.getTitle());
            mailMessage.setText(mail.getText());
            if (!StringUtils.isEmpty(mail.getCc())) {
                mailMessage.setCc(mail.getCc().split(";"));
            }
            if (!StringUtils.isEmpty(mail.getBcc())) {
                mailMessage.setBcc(mail.getBcc().split(";"));
            }

//            if (mail.getMultipartFiles() != null) {
////                for (MultipartFile multipartFile : mail.getMultipartFiles()) {
////                    mailMessage.addAttachment(multipartFile.getOriginalFilename(),multipartFile);
////                }
////            }

            if (StringUtils.isEmpty(mail.getSentDate())) {
                LocalDateTime localDateTime = LocalDateTime.now();
                mail.setSentDate(localDateTime.toString());
            }
            mailSender.send(mailMessage);
            LOGGER.info("邮箱发送成功");
            return true;
        } catch (MailException e) {
            LOGGER.error("邮件发送失败:从:{}----》:{}", mail.getFrom(), mail.getTo(), e);
            return false;
        }
    }
}
