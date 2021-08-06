package com.leijendary.spring.notificationtemplate.data;

import lombok.Data;

@Data
public class SmsSendData {

    private String to;
    private String message;
}
