package com.leijendary.spring.template.notification.api.v1.mapper

import com.leijendary.spring.template.notification.api.v1.model.DeviceRegisterRequest
import com.leijendary.spring.template.notification.entity.Device
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers.getMapper
import java.util.*

@Mapper
interface DeviceMapper {
    companion object {
        val INSTANCE: DeviceMapper = getMapper(DeviceMapper::class.java)
    }

    fun toEntity(userId: UUID, deviceRegisterRequest: DeviceRegisterRequest): Device
}
