package com.leijendary.spring.notificationtemplate.data.response.v1;

import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class SampleResponseV1 implements Serializable {

    private long id;
    private String column1;
    private String column2;
    private OffsetDateTime createdDate;
    private String createdBy;
    private OffsetDateTime lastModifiedDate;
    private String lastModifiedBy;
}
