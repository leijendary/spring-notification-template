package com.leijendary.spring.template.notification.core.extension

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

inline fun <reified T> T.logger(): Logger {
    return getLogger(T::class.java)
}
