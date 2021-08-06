package com.leijendary.spring.notificationtemplate.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("google")
public interface SmsClient {
}
