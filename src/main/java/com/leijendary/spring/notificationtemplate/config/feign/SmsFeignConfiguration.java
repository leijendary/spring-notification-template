package com.leijendary.spring.notificationtemplate.config.feign;

import com.leijendary.spring.notificationtemplate.config.properties.SmsProperties;
import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class SmsFeignConfiguration {

    private final ObjectFactory<HttpMessageConverters> messageConverters;
    private final SmsProperties smsProperties;

    @Bean
    public RequestInterceptor requestInterceptor() {
        final var sender = smsProperties.getSender();
        final var username = smsProperties.getUsername();
        final var password = smsProperties.getPassword();

        return template -> {
            template.query("sender", sender);
            template.query("username", username);
            template.query("password", password);
        };
    }

    @Bean
    public Encoder feignFormEncoder() {
        return new FormEncoder(new SpringEncoder(this.messageConverters));
    }
}
