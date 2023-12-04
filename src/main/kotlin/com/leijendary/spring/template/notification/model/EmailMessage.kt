package com.leijendary.spring.template.notification.model

data class EmailMessage(val to: String, val templateId: String, val parameters: Map<String, Any>)
