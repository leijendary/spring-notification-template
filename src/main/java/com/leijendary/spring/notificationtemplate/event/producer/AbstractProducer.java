package com.leijendary.spring.notificationtemplate.event.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static org.springframework.kafka.support.KafkaHeaders.MESSAGE_KEY;
import static org.springframework.messaging.support.MessageBuilder.withPayload;

@Component
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractProducer<V> {

    public Message<V> messageWithKey(final String key, final V value) {
        return withPayload(value)
                .setHeader(MESSAGE_KEY, key)
                .build();
    }

    public Message<V> message(final V value) {
        return withPayload(value).build();
    }
}
