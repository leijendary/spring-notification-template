package com.leijendary.spring.template.notification.core.exception

class ResourceNotUniqueException(val source: List<String>, val value: String) : RuntimeException()
