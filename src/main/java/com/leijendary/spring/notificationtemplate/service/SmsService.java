package com.leijendary.spring.notificationtemplate.service;

import org.springframework.stereotype.Service;

@Service
public interface SmsService {

    void send(final String to, final String message);
}
