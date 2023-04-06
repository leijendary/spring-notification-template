package com.leijendary.spring.template.notification.client

import com.leijendary.spring.template.notification.core.config.properties.InfoProperties
import com.leijendary.spring.template.notification.core.extension.AnyUtil.toJson
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.ses.SesClient
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest

@Component
class EmailClient(infoProperties: InfoProperties, private val sesClient: SesClient) {
    private val from = infoProperties.api.contact!!.email

    fun send(to: String, template: String, parameters: Map<String, String>) {
        val templatedEmail = SendTemplatedEmailRequest.builder()
            .source(from)
            .destination {
                it.toAddresses(to)
            }
            .template(template)
            .templateData(parameters.toJson())
            .build()

        sesClient.sendTemplatedEmail(templatedEmail)
    }
}
