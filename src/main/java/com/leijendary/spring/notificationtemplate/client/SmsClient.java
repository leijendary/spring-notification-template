package com.leijendary.spring.notificationtemplate.client;

import com.leijendary.spring.notificationtemplate.config.feign.FormUrlEncodedFeignConfiguration;
import com.leijendary.spring.notificationtemplate.data.SmsSendData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "sms", configuration = FormUrlEncodedFeignConfiguration.class)
public interface SmsClient {

    @PostMapping(value = "api.php/sendsms", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    String send(@RequestBody final SmsSendData data);
}
