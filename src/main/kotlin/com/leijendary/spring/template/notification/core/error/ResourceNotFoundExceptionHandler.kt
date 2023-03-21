package com.leijendary.spring.template.notification.core.error

import com.leijendary.spring.template.notification.core.exception.ResourceNotFoundException
import com.leijendary.spring.template.notification.core.model.ErrorModel
import com.leijendary.spring.template.notification.core.util.RequestContext.locale
import org.springframework.context.MessageSource
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(3)
class ResourceNotFoundExceptionHandler(private val messageSource: MessageSource) {
    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun catchResourceNotFound(exception: ResourceNotFoundException): List<ErrorModel> {
        val source = exception.source
        val code = "error.resource.notFound"
        val arguments = arrayOf(source.joinToString("."), exception.identifier)
        val message = messageSource.getMessage(code, arguments, locale)
        val error = ErrorModel(source, code, message)

        return listOf(error)
    }
}
