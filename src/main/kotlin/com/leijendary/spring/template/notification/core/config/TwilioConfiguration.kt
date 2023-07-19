package com.leijendary.spring.template.notification.core.config

import com.leijendary.spring.template.notification.core.config.properties.TwilioProperties
import com.twilio.Twilio
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration

@Configuration
class TwilioConfiguration(private val twilioProperties: TwilioProperties) {
    @PostConstruct
    fun init() {
        Twilio.init(twilioProperties.accountSid, twilioProperties.authToken)
    }
}
