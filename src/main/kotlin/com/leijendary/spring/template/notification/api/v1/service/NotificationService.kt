package com.leijendary.spring.template.notification.api.v1.service

import com.leijendary.spring.template.notification.api.v1.mapper.NotificationMapper
import com.leijendary.spring.template.notification.api.v1.model.NotificationResponse
import com.leijendary.spring.template.notification.client.NotificationClient
import com.leijendary.spring.template.notification.core.datasource.transactional
import com.leijendary.spring.template.notification.entity.Notification
import com.leijendary.spring.template.notification.model.Status.READ
import com.leijendary.spring.template.notification.repository.DeviceRepository
import com.leijendary.spring.template.notification.repository.NotificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationService(
    private val deviceRepository: DeviceRepository,
    private val notificationClient: NotificationClient,
    private val notificationRepository: NotificationRepository
) {
    companion object {
        private val MAPPER = NotificationMapper.INSTANCE
    }

    fun list(userId: UUID, pageable: Pageable): Page<NotificationResponse> {
        return notificationRepository
            .findByUserId(userId, pageable)
            .map { MAPPER.toResponse(it) }
    }

    fun create(notification: Notification) {
        notificationRepository.save(notification)

        val userId = notification.userId
        val title = notification.title
        val body = notification.body
        val imageUrl = notification.imageUrl

        deviceRepository.streamByUserId(userId).use { stream ->
            stream.parallel().forEach { notificationClient.send(it.platform, it.token, title, body, imageUrl) }
        }
    }

    fun get(userId: UUID, id: UUID): NotificationResponse {
        val notification = notificationRepository.findFirstByIdAndUserIdOrThrow(id, userId)

        notification.status = READ

        notificationRepository.save(notification)

        return MAPPER.toResponse(notification)
    }

    fun delete(userId: UUID, id: UUID) {
        transactional {
            notificationRepository
                .findFirstByIdAndUserIdOrThrow(id, userId)
                .let {
                    notificationRepository.delete(it)
                }
        }
    }
}