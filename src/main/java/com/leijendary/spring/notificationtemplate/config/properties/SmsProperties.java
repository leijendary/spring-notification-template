package com.leijendary.spring.notificationtemplate.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms")
@Data
public class SmsProperties {

    private String sender;
    private String username;
    private String password;
}
