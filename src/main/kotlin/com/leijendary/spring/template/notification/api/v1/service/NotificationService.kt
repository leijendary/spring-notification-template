package com.leijendary.spring.template.notification.api.v1.service

import com.leijendary.spring.template.notification.api.v1.mapper.NotificationMapper
import com.leijendary.spring.template.notification.api.v1.model.NotificationCountResponse
import com.leijendary.spring.template.notification.api.v1.model.NotificationResponse
import com.leijendary.spring.template.notification.entity.Notification.Status
import com.leijendary.spring.template.notification.entity.Notification.Status.READ
import com.leijendary.spring.template.notification.repository.NotificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class NotificationService(private val notificationRepository: NotificationRepository) {
    fun list(userId: UUID, pageable: Pageable): Page<NotificationResponse> {
        return notificationRepository
            .findByUserId(userId, pageable)
            .map(NotificationMapper.INSTANCE::toResponse)
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
            .let(notificationRepository::delete)
    }

    fun count(userId: UUID, status: Status): NotificationCountResponse {
        val count = notificationRepository.countByStatus(status)

        return NotificationCountResponse(count, status)
    }
}
