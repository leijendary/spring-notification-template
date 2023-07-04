package com.leijendary.spring.template.notification.api.v1.model

import com.leijendary.spring.template.notification.entity.Notification.Status

data class NotificationCountResponse(val count: Long, val status: Status)
