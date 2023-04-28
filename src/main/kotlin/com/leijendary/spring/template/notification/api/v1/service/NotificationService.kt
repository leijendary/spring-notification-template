package com.leijendary.spring.template.notification.api.v1.service

import com.leijendary.spring.template.notification.api.v1.mapper.NotificationMapper
import com.leijendary.spring.template.notification.api.v1.model.NotificationResponse
import com.leijendary.spring.template.notification.client.NotificationClient
import com.leijendary.spring.template.notification.entity.Notification
import com.leijendary.spring.template.notification.entity.Notification.Status.READ
import com.leijendary.spring.template.notification.repository.DeviceRepository
import com.leijendary.spring.template.notification.repository.NotificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class NotificationService(
    private val deviceRepository: DeviceRepository,
    private val notificationClient: NotificationClient,
    private val notificationRepository: NotificationRepository
) {
    fun list(userId: UUID, pageable: Pageable): Page<NotificationResponse> {
        return notificationRepository
            .findByUserId(userId, pageable)
            .map { NotificationMapper.INSTANCE.toResponse(it) }
    }

    fun create(notification: Notification) {
        notificationRepository.save(notification)

        val userId = notification.userId
        val title = notification.title
        val body = notification.body
        val image = notification.image

        deviceRepository.streamByUserId(userId).use { stream ->
            stream.forEach {
                notificationClient
                    .send(it.platform, it.endpoint, title, body, image)
                    .exceptionally { _ ->
                        deviceRepository.delete(it)

                        null
                    }
            }
        }
    }

    fun get(userId: UUID, id: UUID): NotificationResponse {
        val notification = notificationRepository.findFirstByIdAndUserIdOrThrow(id, userId).apply { status = READ }

        notificationRepository.save(notification)

        return NotificationMapper.INSTANCE.toResponse(notification)
    }

    @Transactional
    fun delete(userId: UUID, id: UUID) {
        notificationRepository
            .findFirstByIdAndUserIdOrThrow(id, userId)
            .let {
                notificationRepository.delete(it)
            }
    }
}
