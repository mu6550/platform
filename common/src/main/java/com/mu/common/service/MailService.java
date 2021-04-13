package com.mu.common.service;

import com.mu.common.entity.Mail;
import com.mu.common.util.R;

public interface MailService {

    /**
     * 发送邮件
     *
     * @param mail
     * @return
     */
    R sendMail(Mail mail);

    /**
     * 获取发件人信息
     *
     * @return
     */
    String getMailSendFrom();

    R saveMsg(Mail mail);
}
