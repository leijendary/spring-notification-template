package com.leijendary.spring.notificationtemplate.data;

import lombok.Data;

@Data
public class SmsSendData {

    private String sender;
    private String username;
    private String password;
    private String to;
    private String message;
}
