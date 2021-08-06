package com.leijendary.spring.notificationtemplate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Device extends SnowflakeIdModel {

    private long userId;
    private String username;
    private String audience;
    private String type;
    private String deviceId;
    private OffsetDateTime createdDate;
}
