package com.leijendary.spring.notificationtemplate.exception;

public class SmsNotSentException extends RuntimeException {

    public SmsNotSentException(final String message) {
        super(message);
    }
}
