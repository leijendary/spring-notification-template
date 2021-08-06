package com.leijendary.spring.notificationtemplate.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Profile("aws")
@Service
@RequiredArgsConstructor
public class AwsSmsService extends AbstractService implements SmsService {

    private final AmazonSNSClient amazonSNSClient;

    @Override
    public void send(String to, String message) {
        final var smsTypeAttribute = new MessageAttributeValue()
                .withStringValue("Transactional")
                .withDataType("String");
        final var smsAttributes = new HashMap<String, MessageAttributeValue>();
        smsAttributes.put("AWS.SNS.SMS.SMSType", smsTypeAttribute);

        final var publishRequest = new PublishRequest()
                .withPhoneNumber(to)
                .withMessage(message)
                .withMessageAttributes(smsAttributes);

        amazonSNSClient.publish(publishRequest);
    }
}
