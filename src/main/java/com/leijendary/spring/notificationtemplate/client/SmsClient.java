package com.leijendary.spring.notificationtemplate.client;

import com.leijendary.spring.notificationtemplate.config.feign.SmsFeignConfiguration;
import com.leijendary.spring.notificationtemplate.data.SmsSendData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sms", configuration = SmsFeignConfiguration.class)
public interface SmsClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String send(@RequestBody SmsSendData data);
}
