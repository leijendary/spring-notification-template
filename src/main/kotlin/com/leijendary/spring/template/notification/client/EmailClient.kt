package com.leijendary.spring.template.notification.client

import com.leijendary.spring.template.notification.core.config.properties.InfoProperties
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component
class EmailClient(private val infoProperties: InfoProperties, private val javaMailSender: JavaMailSender) {
    fun send(to: String, subject: String, htmlText: String, plainText: String?) {
        val message = javaMailSender.createMimeMessage()

        MimeMessageHelper(message, true).apply {
            setTo(to)
            setFrom(infoProperties.api.contact!!.email)
            setSubject(subject)
            setText(htmlText, true)

            plainText?.let { setText(it) }
        }

        javaMailSender.send(message)
    }
}
