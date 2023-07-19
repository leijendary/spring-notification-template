package com.leijendary.spring.template.notification.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("twilio")
class TwilioProperties {
    lateinit var accountSid: String
    lateinit var authToken: String
    lateinit var phone: String
}
