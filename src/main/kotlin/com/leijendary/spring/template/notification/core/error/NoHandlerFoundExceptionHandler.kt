package com.leijendary.spring.template.notification.core.error

import com.leijendary.spring.template.notification.core.model.ErrorModel
import com.leijendary.spring.template.notification.core.util.RequestContext.locale
import org.springframework.context.MessageSource
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
@Order(1)
class NoHandlerFoundExceptionHandler(private val messageSource: MessageSource) {
    @ExceptionHandler(NoHandlerFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun catchNoHandlerFound(exception: NoHandlerFoundException): List<ErrorModel> {
        val code = "error.mapping.notFound"
        val arguments = arrayOf(exception.message)
        val message = messageSource.getMessage(code, arguments, locale)
        val error = ErrorModel(mutableListOf("request", "path"), code, message)

        return listOf(error)
    }
}
