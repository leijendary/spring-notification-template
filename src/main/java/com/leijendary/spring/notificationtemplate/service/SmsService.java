package com.leijendary.spring.notificationtemplate.service;

import com.leijendary.spring.notificationtemplate.client.SmsClient;
import com.leijendary.spring.notificationtemplate.data.SmsSendData;
import com.leijendary.spring.notificationtemplate.exception.SmsNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService extends AbstractService {

    private final SmsClient smsClient;

    public void send(final SmsSendData data) {
        final var result = smsClient.send(data);

        log.info("SmsClient#send(data) result: {}", result);

        if (result.equals("-100")) {
            throw new SmsNotSentException(result);
        }
    }
}
