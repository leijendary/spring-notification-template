package com.leijendary.spring.notificationtemplate.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.leijendary.spring.notificationtemplate.client")
public class FeignConfiguration {
}
