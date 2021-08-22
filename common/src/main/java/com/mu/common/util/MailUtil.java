package com.mu.common.util;

import com.mu.common.entity.Mail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;

public class MailUtil {

    public static SimpleMailMessage send(Mail mail) {
//        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
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
            if (mail.getMultipartFiles() != null) {
                for (MultipartFile multipartFile : mail.getMultipartFiles()) {
                    mailMessage.setTo(multipartFile.getOriginalFilename());
                }
            }
            if (StringUtils.isEmpty(mail.getSentDate())) {
                LocalDateTime localDateTime = LocalDateTime.now();
                mail.setSentDate(localDateTime.toString());
            }
          return mailMessage;
//            LOGGER.info("email send success");
//        } catch (MailException e) {
//            LOGGER.error("email send fail from :{} to :{}", mail.getFrom(), mail.getTo(), e.getMessage());
//            throw new MessageAggregationException(e.getMessage(), e.getCause());
//        }
    }
}
