package com.leijendary.spring.template.notification.core.extension

import com.leijendary.spring.template.notification.core.util.BeanContainer.objectMapper

fun Any.toJson(): String {
    return objectMapper.writeValueAsString(this)
}
