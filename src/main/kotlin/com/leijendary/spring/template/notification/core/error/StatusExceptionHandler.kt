package com.leijendary.spring.template.notification.core.error

import com.leijendary.spring.template.notification.core.exception.StatusException
import com.leijendary.spring.template.notification.core.model.ErrorModel
import com.leijendary.spring.template.notification.core.util.RequestContext.locale
import org.springframework.context.MessageSource
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(3)
class StatusExceptionHandler(private val messageSource: MessageSource) {
    @ExceptionHandler(StatusException::class)
    fun catchStatus(exception: StatusException): ResponseEntity<List<ErrorModel>> {
        val source = exception.source
        val code = exception.code
        val arguments = exception.arguments
        val message = messageSource.getMessage(code, arguments, locale)
        val status = exception.status
        val error = ErrorModel(source, code, message)
        val errors = listOf(error)

        return ResponseEntity
            .status(status)
            .body(errors)
    }
}
