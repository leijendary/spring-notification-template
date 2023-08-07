package com.leijendary.spring.template.notification.api.v1.mapper

import com.leijendary.spring.template.notification.api.v1.model.NotificationResponse
import com.leijendary.spring.template.notification.entity.Notification
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers.getMapper

@Mapper
interface NotificationMapper {
    companion object {
        val INSTANCE: NotificationMapper = getMapper(NotificationMapper::class.java)
    }

    fun toResponse(notification: Notification): NotificationResponse
}
