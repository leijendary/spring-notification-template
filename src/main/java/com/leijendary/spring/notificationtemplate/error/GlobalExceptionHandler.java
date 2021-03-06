package com.leijendary.spring.notificationtemplate.error;

import com.leijendary.spring.notificationtemplate.data.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.leijendary.spring.notificationtemplate.util.RequestContextUtil.getLocale;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse catchException(final Exception exception) {
        final var code = "error.generic";
        final var error = messageSource.getMessage(code, new Object[0], getLocale());

        log.error("Global Exception", exception);

        return ErrorResponse.builder()
                .addError(error, code, exception.getMessage())
                .build();
    }
}
