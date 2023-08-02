package com.leijendary.spring.template.notification.core.util

import com.leijendary.spring.template.notification.core.exception.StatusException
import com.leijendary.spring.template.notification.core.util.BeanContainer.authProperties
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.i18n.LocaleContextHolder.getLocale
import org.springframework.context.i18n.LocaleContextHolder.getTimeZone
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST
import org.springframework.web.context.request.RequestContextHolder.getRequestAttributes
import org.springframework.web.context.request.ServletRequestAttributes
import java.net.URI
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

const val HEADER_USER_ID = "X-User-ID"

private val sourceAuth = listOf("header", HEADER_USER_ID)

object RequestContext {
    val currentRequest: HttpServletRequest?
        get() {
            val attributes = getRequestAttributes() as? ServletRequestAttributes

            return attributes?.request
        }

    val userIdOrNull: UUID?
        get() = currentRequest?.getHeader(HEADER_USER_ID)?.let(UUID::fromString)

    val userIdOrThrow: UUID
        get() = userIdOrNull ?: throw StatusException(sourceAuth, "access.session.notFound", UNAUTHORIZED)

    val userIdOrSystem: String
        get() = userIdOrNull?.toString() ?: authProperties.system.principal

    val uri: URI?
        get() {
            val request = currentRequest ?: return null
            val contextPath = request.contextPath
            var url = request.requestURI.replaceFirst(contextPath.toRegex(), "")

            request.queryString?.also { url += "?$it" }

            return URI.create(url)
        }

    val timeZone: TimeZone
        get() = getTimeZone()

    val zoneId: ZoneId
        get() = timeZone.toZoneId()

    val locale: Locale
        get() = getLocale()

    val language: String
        get() = locale.language

    val now: OffsetDateTime
        get() = OffsetDateTime.now(zoneId)

    /**
     * Added this here as a utility function to cache objects in to the request scope.
     * For example, you have an external API call that is being called multiple times
     * within the same request lifecycle, instead of calling the said external API call
     * once and passing the result in to multiple functions, save the result in to this
     * function and reuse the value without passing it into multiple functions.
     */
    inline fun <reified T : Any> getOrSetAttribute(name: String, default: () -> T): T {
        val requestAttributes = getRequestAttributes()!!
        var value = requestAttributes.getAttribute(name, SCOPE_REQUEST) as? T

        if (value !== null) {
            return value
        }

        value = default()

        requestAttributes.setAttribute(name, value, SCOPE_REQUEST)

        return value
    }
}
