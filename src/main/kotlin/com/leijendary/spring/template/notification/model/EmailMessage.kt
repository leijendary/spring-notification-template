package com.leijendary.spring.template.notification.model

data class EmailMessage(val to: String, val template: String, val params: Map<String, String>)
