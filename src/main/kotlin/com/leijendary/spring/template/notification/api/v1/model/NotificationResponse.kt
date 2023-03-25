package com.leijendary.spring.template.notification.api.v1.model

import com.leijendary.spring.template.notification.model.Status
import java.time.OffsetDateTime
import java.util.*

data class NotificationResponse(
    val id: UUID,
    val userId: UUID,
    val title: String,
    val body: String,
    val imageUrl: String?,
    val status: Status,
    val createdAt: OffsetDateTime
)