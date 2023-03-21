package com.leijendary.spring.template.notification.entity

import com.leijendary.spring.template.notification.core.entity.UUIDEntity
import com.leijendary.spring.template.notification.core.util.RequestContext.now
import jakarta.persistence.Entity
import org.springframework.data.annotation.CreatedDate
import java.time.OffsetDateTime
import java.util.*

@Entity
class Notification : UUIDEntity() {
    lateinit var userId: UUID
    lateinit var title: String
    lateinit var body: String
    var imageUrl: String? = null
    var isRead: Boolean = false

    @CreatedDate
    var createdAt: OffsetDateTime = now
}
