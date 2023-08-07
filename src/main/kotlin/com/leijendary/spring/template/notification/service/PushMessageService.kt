package com.leijendary.spring.template.notification.service

import com.leijendary.spring.template.notification.client.NotificationClient
import com.leijendary.spring.template.notification.core.datasource.transactional
import com.leijendary.spring.template.notification.mapper.NotificationMapper
import com.leijendary.spring.template.notification.model.PushMessage
import com.leijendary.spring.template.notification.repository.DeviceRepository
import com.leijendary.spring.template.notification.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class PushMessageService(
    private val deviceRepository: DeviceRepository,
    private val notificationClient: NotificationClient,
    private val notificationRepository: NotificationRepository
) {
    fun send(pushMessage: PushMessage) {
        val notification = NotificationMapper.INSTANCE.toEntity(pushMessage)

        notificationRepository.save(notification)

        transactional(true) {
            deviceRepository.streamByUserId(notification.userId).use { stream ->
                stream.forEach {
                    notificationClient
                        .send(it.platform, it.endpoint, notification.title, notification.body, notification.image)
                        .exceptionally { _ -> deviceRepository.delete(it); null }
                }
            }
        }
    }
}
