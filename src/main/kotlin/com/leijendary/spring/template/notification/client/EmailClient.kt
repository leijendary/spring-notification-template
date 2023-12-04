package com.leijendary.spring.template.notification.client

import com.leijendary.spring.template.notification.core.config.properties.InfoProperties
import com.leijendary.spring.template.notification.core.extension.logger
import com.sendgrid.Method.POST
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Email
import com.sendgrid.helpers.mail.objects.Personalization
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class EmailClient(infoProperties: InfoProperties, private val sendGrid: SendGrid) {
    private val log = logger()
    private val email = Email(infoProperties.api.contact!!.email, infoProperties.api.contact!!.name)

    fun send(to: String, templateId: String, parameters: Map<String, Any>) {
        val personalization = Personalization().apply { addTo(Email(to)) }

        parameters.forEach { (key, value) -> personalization.addDynamicTemplateData(key, value) }

        val mail = Mail().apply {
            from = email
            this.templateId = templateId
            addPersonalization(personalization)
        }
        val request = Request().apply {
            method = POST
            endpoint = "mail/send"
            body = mail.build()
        }
        val response = sendGrid.api(request)
        val status = HttpStatus.valueOf(response.statusCode)

        if (status.isError) {
            log.error("Email error: ${response.statusCode} ${response.body}")
        } else {
            log.info("Email response: ${response.statusCode} ${response.body}")
        }
    }
}
