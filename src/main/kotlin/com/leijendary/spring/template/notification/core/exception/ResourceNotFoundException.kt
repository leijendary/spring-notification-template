package com.leijendary.spring.template.notification.core.exception

class ResourceNotFoundException(val source: List<Any>, val identifier: Any) : RuntimeException()
