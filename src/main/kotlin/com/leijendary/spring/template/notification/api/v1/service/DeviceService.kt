package com.leijendary.spring.template.notification.api.v1.service

import com.leijendary.spring.template.notification.api.v1.mapper.DeviceMapper
import com.leijendary.spring.template.notification.api.v1.model.DeviceDeregisterRequest
import com.leijendary.spring.template.notification.api.v1.model.DeviceRegisterRequest
import com.leijendary.spring.template.notification.client.NotificationClient
import com.leijendary.spring.template.notification.core.datasource.transactional
import com.leijendary.spring.template.notification.repository.DeviceRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeviceService(
    private val deviceRepository: DeviceRepository,
    private val notificationClient: NotificationClient
) {
    companion object {
        private val MAPPER = DeviceMapper.INSTANCE
    }

    fun register(userId: UUID, request: DeviceRegisterRequest) {
        val platform = request.platform!!
        val token = request.token!!
        val device = transactional(readOnly = true) {
            deviceRepository
                .findFirstByToken(token)
                ?.apply { this.userId = userId }
        } ?: MAPPER.toEntity(userId, request)

        if (device.endpoint.isBlank()) {
            val endpoint = notificationClient.createEndpoint(platform, token) ?: return

            device.endpoint = endpoint
        }

        deviceRepository.save(device)
    }

    fun deregister(userId: UUID, request: DeviceDeregisterRequest) {
        val token = request.token!!
        val device = transactional(readOnly = true) {
            deviceRepository.findFirstByUserIdAndTokenOrThrow(userId, token)
        }!!

        transactional {
            deviceRepository.delete(device)

            val endpoint = device.endpoint

            notificationClient.deleteEndpoint(endpoint)
        }
    }
}