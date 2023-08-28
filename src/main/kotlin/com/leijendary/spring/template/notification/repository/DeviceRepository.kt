package com.leijendary.spring.template.notification.repository

import com.leijendary.spring.template.notification.core.exception.ResourceNotFoundException
import com.leijendary.spring.template.notification.entity.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Stream

private val sourceToken = listOf("data", "Device", "token")

interface DeviceRepository : JpaRepository<Device, Long> {
    @Transactional(readOnly = true)
    fun findFirstByToken(token: String): Device?

    @Transactional(readOnly = true)
    fun findFirstByUserIdAndToken(userId: UUID, token: String): Device?

    @Transactional(readOnly = true)
    fun findFirstByUserIdAndTokenOrThrow(userId: UUID, token: String): Device {
        return findFirstByUserIdAndToken(userId, token) ?: throw ResourceNotFoundException(sourceToken, token)
    }

    @Transactional(readOnly = true)
    fun streamByUserId(userId: UUID): Stream<Device>
}
