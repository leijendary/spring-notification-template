package com.leijendary.spring.template.notification.message

import com.leijendary.spring.template.notification.api.v1.mapper.NotificationMapper
import com.leijendary.spring.template.notification.api.v1.service.NotificationService
import com.leijendary.spring.template.notification.client.EmailClient
import com.leijendary.spring.template.notification.client.SmsClient
import com.leijendary.spring.template.notification.core.extension.toClass
import com.leijendary.spring.template.notification.message.Topic.NOTIFICATION_EMAIL
import com.leijendary.spring.template.notification.message.Topic.NOTIFICATION_PUSH
import com.leijendary.spring.template.notification.message.Topic.NOTIFICATION_SMS
import com.leijendary.spring.template.notification.model.EmailMessage
import com.leijendary.spring.template.notification.model.PushMessage
import com.leijendary.spring.template.notification.model.SmsMessage
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NotificationConsumer(
    private val emailClient: EmailClient,
    private val notificationService: NotificationService,
    private val smsClient: SmsClient
) {
    @KafkaListener(topics = ["\${spring.kafka.topic.$NOTIFICATION_EMAIL.name}"])
    fun email(json: String) {
        val emailMessage: EmailMessage = json.toClass()

        emailClient.send(emailMessage.to, emailMessage.templateId, emailMessage.parameters)
    }

    @KafkaListener(topics = ["\${spring.kafka.topic.$NOTIFICATION_PUSH.name}"])
    fun push(json: String) {
        val pushMessage: PushMessage = json.toClass()
        val notification = NotificationMapper.INSTANCE.toEntity(pushMessage)

        notificationService.create(notification)
    }

    @KafkaListener(topics = ["\${spring.kafka.topic.$NOTIFICATION_SMS.name}"])
    fun sms(json: String) {
        val smsMessage: SmsMessage = json.toClass()

        smsClient.send(smsMessage.to, smsMessage.message)
    }
}
