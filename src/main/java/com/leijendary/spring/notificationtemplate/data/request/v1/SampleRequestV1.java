package com.leijendary.spring.notificationtemplate.data.request.v1;

import com.leijendary.spring.notificationtemplate.validator.annotation.v1.FieldsNotEqualV1;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldsNotEqualV1
public class SampleRequestV1 {

    @NotBlank(message = "validation.required")
    @Size(max = 50, message = "validation.maxLength")
    private String field1;

    private int field2;
}
