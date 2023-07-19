package com.leijendary.spring.template.notification.client

import com.leijendary.spring.template.notification.core.config.properties.TwilioProperties
import com.leijendary.spring.template.notification.core.extension.logger
import com.twilio.rest.api.v2010.account.Message
import com.twilio.rest.api.v2010.account.Message.Status.FAILED
import com.twilio.type.PhoneNumber
import org.springframework.stereotype.Component

@Component
class SmsClient(twilioProperties: TwilioProperties) {
    private val log = logger()
    private val phone = PhoneNumber(twilioProperties.phone)

    fun send(to: String, message: String) {
        val response = Message.creator(PhoneNumber(to), phone, message).create()

        if (response.status == FAILED) {
            log.error("SMS error: ${response.sid} ${response.status} ${response.errorMessage}")
        } else {
            log.info("SMS response: ${response.sid} ${response.status} ${response.dateSent}")
        }
    }
}
