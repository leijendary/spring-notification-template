package com.leijendary.spring.notificationtemplate.event.consumer;

import com.leijendary.schema.AuthSchema;
import com.leijendary.spring.notificationtemplate.factory.DeviceDataFactory;
import com.leijendary.spring.notificationtemplate.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TokenConsumer {

    private final DeviceService deviceService;

    @Bean
    public Consumer<KStream<String, AuthSchema>> tokenCreate() {
        return stream -> stream.foreach((key, value) -> {
            log.info("Token Create: '{}'", value);

            final var deviceData = DeviceDataFactory.of(value);

            deviceService.save(deviceData);
        });
    }

    @Bean
    public Consumer<KStream<String, AuthSchema>> tokenRevoke() {
        return stream -> stream.foreach((key, value) -> {
            log.info("Token Revoke: '{}'", value);

            final var deviceData = DeviceDataFactory.of(value);
            final var deviceId = deviceData.getDeviceId();

            deviceService.deleteByDeviceId(deviceId);
        });
    }
}
