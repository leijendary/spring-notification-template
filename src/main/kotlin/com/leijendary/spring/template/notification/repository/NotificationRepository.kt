package com.leijendary.spring.template.notification.repository

import com.leijendary.spring.template.notification.core.exception.ResourceNotFoundException
import com.leijendary.spring.template.notification.entity.Notification
import com.leijendary.spring.template.notification.entity.Notification.Status
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

private val source = listOf("data", "Notification", "id")

interface NotificationRepository : JpaRepository<Notification, UUID> {
    @Transactional(readOnly = true)
    fun findByUserId(userId: UUID, pageable: Pageable): Page<Notification>

    @Transactional(readOnly = true)
    fun findFirstByIdAndUserId(id: UUID, userId: UUID): Notification?

    @Transactional(readOnly = true)
    fun countByStatus(status: Status): Long

    @Transactional(readOnly = true)
    fun findFirstByIdAndUserIdOrThrow(id: UUID, userId: UUID): Notification {
        return findFirstByIdAndUserId(id, userId) ?: throw ResourceNotFoundException(source, id)
    }
}
