package com.leijendary.spring.notificationtemplate.data;

import lombok.Data;

@Data
public class DeviceData {

    private long userId;
    private String username;
    private String audience;
    private String type;
    private String deviceId;
}
