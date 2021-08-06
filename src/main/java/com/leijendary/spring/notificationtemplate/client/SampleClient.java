package com.leijendary.spring.notificationtemplate.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("google")
public interface SampleClient {

    @GetMapping
    String homepage();
}
