package com.mu.common.controller;


import com.mu.common.entity.Mail;
import com.mu.common.service.MailService;
import com.mu.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/")
    public String index() {
        return mailService.getMailSendFrom();
    }

//    @PostMapping(value = "/sendMail",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public R sendMail(@RequestBody Mail mail, MultipartFile [] files) {
//        mail.setMultipartFiles(files);
//        return mailService.sendMail(mail);
//    }

    @PostMapping(value = "/sendMail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R sendMail(@RequestBody Mail mail, MultipartFile[] files) {
        mail.setMultipartFiles(files);
        return mailService.sendMail(mail);
    }
}
