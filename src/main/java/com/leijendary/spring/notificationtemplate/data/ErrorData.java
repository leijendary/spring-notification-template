package com.leijendary.spring.notificationtemplate.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorData {

    private String source;
    private String code;
    private String message;
}
