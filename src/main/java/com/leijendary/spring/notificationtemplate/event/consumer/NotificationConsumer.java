package com.leijendary.spring.notificationtemplate.event.consumer;

import com.leijendary.schema.NotificationSchema;
import com.leijendary.spring.notificationtemplate.data.SmsSendData;
import com.leijendary.spring.notificationtemplate.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final SmsService smsService;

    @Bean
    public Consumer<KStream<String, NotificationSchema>> notificationSms() {
        return stream -> stream.foreach((key, value) -> {
            log.info("Notification Sms: '{}'", value);

            final var to = value.getTo();
            final var content = value.getContent();
            final var smsData = new SmsSendData();
            smsData.setTo(to);
            smsData.setMessage(content);

            smsService.send(smsData);
        });
    }

    @Bean
    public Consumer<KStream<String, NotificationSchema>> notificationEmail() {
        return stream -> stream.foreach((key, value) -> log.info("Notification Email: '{}'", value));
    }
}
