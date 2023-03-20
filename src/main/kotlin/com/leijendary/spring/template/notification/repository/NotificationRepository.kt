package com.leijendary.spring.template.notification.repository

import com.leijendary.spring.template.notification.core.exception.ResourceNotFoundException
import com.leijendary.spring.template.notification.entity.Notification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

private val source = listOf("data", "Notification", "id")

interface NotificationRepository : JpaRepository<Notification, UUID> {
    fun findByUserId(userId: UUID, pageable: Pageable): Page<Notification>

    fun findFirstByIdAndUserId(id: UUID, userId: UUID): Notification?

    fun findFirstByIdAndUserIdOrThrow(id: UUID, userId: UUID): Notification {
        return findFirstByIdAndUserId(id, userId) ?: throw ResourceNotFoundException(source, id)
    }
}