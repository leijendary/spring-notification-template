package com.leijendary.spring.template.notification.entity

import com.leijendary.spring.template.notification.core.entity.IdentityEntity
import com.leijendary.spring.template.notification.core.util.RequestContext.now
import jakarta.persistence.Entity
import org.springframework.data.annotation.CreatedDate
import java.time.OffsetDateTime
import java.util.*

@Entity
class Device : IdentityEntity() {
    var userId: UUID? = null
    var token: String = ""
    var platform: String = ""
    var endpoint: String = ""

    @CreatedDate
    var createdAt: OffsetDateTime = now
}
