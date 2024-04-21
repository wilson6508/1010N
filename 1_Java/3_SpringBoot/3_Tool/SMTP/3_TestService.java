package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    JavaMailSender mailSender;

    public void test() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("wilsonhuang@eland.com.tw");
        message.setSubject("測試發信");
        message.setText("測試發信");
        mailSender.send(message);
    }

}