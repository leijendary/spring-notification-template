package com.leijendary.spring.notificationtemplate.config;

import com.leijendary.spring.notificationtemplate.config.properties.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        AspectProperties.class,
        AuthProperties.class,
        CorsProperties.class,
        InfoProperties.class,
        SmsProperties.class })
public class PropertiesConfiguration {
}
