package com.leijendary.spring.notificationtemplate.service;

import com.leijendary.spring.notificationtemplate.client.SmsClient;
import com.leijendary.spring.notificationtemplate.config.properties.SmsProperties;
import com.leijendary.spring.notificationtemplate.data.SmsSendData;
import com.leijendary.spring.notificationtemplate.exception.SmsNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VendorSmsService extends AbstractService implements SmsService {

    private final SmsClient smsClient;
    private final SmsProperties smsProperties;

    public void send(final String to, final String message) {
        final var sender = smsProperties.getSender();
        final var username = smsProperties.getUsername();
        final var password = smsProperties.getPassword();
        final var smsData = new SmsSendData();
        smsData.setUnicode("u");
        smsData.setSender(sender);
        smsData.setUser(username);
        smsData.setPass(password);
        smsData.setTo(to);
        smsData.setMessage(message);

        final var result = smsClient.send(smsData);

        log.info("SmsClient#send(data) result: {}", result);

        if (result.equals("-100")) {
            throw new SmsNotSentException(result);
        }
    }
}
