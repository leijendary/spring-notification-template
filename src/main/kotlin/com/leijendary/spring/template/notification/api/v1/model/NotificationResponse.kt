package com.leijendary.spring.template.notification.api.v1.model

import com.leijendary.spring.template.notification.entity.Notification.Status
import java.time.OffsetDateTime
import java.util.*

data class NotificationResponse(
    val id: UUID,
    val title: String,
    val body: String,
    val image: String?,
    val status: Status,
    val createdAt: OffsetDateTime
)
