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
    companion object {
        private val MAPPER = NotificationMapper.INSTANCE
    }

    @KafkaListener(topics = ["\${spring.kafka.topic.$NOTIFICATION_EMAIL.name}"])
    fun email(json: String) {
        val emailMessage = json.toClass(EmailMessage::class)
        val to = emailMessage.to
        val subject = emailMessage.subject
        val htmlText = emailMessage.htmlText
        val plainText = emailMessage.plainText

        emailClient.send(to, subject, htmlText, plainText)
    }

    @KafkaListener(topics = ["\${spring.kafka.topic.$NOTIFICATION_PUSH.name}"])
    fun push(json: String) {
        val pushMessage = json.toClass(PushMessage::class)
        val notification = MAPPER.toEntity(pushMessage)

        notificationService.create(notification)
    }

    @KafkaListener(topics = ["\${spring.kafka.topic.$NOTIFICATION_SMS.name}"])
    fun sms(json: String) {
        val smsMessage = json.toClass(SmsMessage::class)
        val to = smsMessage.to
        val message = smsMessage.message

        smsClient.send(to, message)
    }
}