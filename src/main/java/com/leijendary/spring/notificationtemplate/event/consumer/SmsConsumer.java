package com.leijendary.spring.notificationtemplate.event.consumer;

import com.leijendary.spring.notificationtemplate.client.SmsClient;
import com.leijendary.spring.notificationtemplate.event.schema.NotificationSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SmsConsumer {

    private final SmsClient smsClient;

    @Bean
    public Consumer<KStream<String, NotificationSchema>> notificationSms() {
        return stream -> stream.foreach((key, value) -> log.info("Created: '{}', '{}'", key, value));
    }
}
