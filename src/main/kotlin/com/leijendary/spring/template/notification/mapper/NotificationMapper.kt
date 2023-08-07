package com.leijendary.spring.template.notification.mapper

import com.leijendary.spring.template.notification.entity.Notification
import com.leijendary.spring.template.notification.model.PushMessage
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
interface NotificationMapper {
    companion object {
        val INSTANCE: NotificationMapper = Mappers.getMapper(NotificationMapper::class.java)
    }

    fun toEntity(pushMessage: PushMessage): Notification
}
