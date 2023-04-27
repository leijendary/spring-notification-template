package com.leijendary.spring.template.notification.entity

import com.leijendary.spring.template.notification.core.entity.UUIDEntity
import com.leijendary.spring.template.notification.core.util.RequestContext.now
import com.leijendary.spring.template.notification.model.Status
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import org.springframework.data.annotation.CreatedDate
import java.time.OffsetDateTime
import java.util.*

@Entity
class Notification : UUIDEntity() {
    lateinit var userId: UUID
    lateinit var title: String
    lateinit var body: String
    var image: String? = null

    @Enumerated(STRING)
    var status: Status = Status.NEW

    @CreatedDate
    var createdAt: OffsetDateTime = now
}
