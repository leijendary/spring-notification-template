package com.leijendary.spring.notificationtemplate.data;

import lombok.Data;

@Data
public class SmsSendData {

    private String unicode;
    private String sender;
    private String user;
    private String pass;
    private String to;
    private String message;
}
