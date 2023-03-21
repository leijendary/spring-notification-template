package com.leijendary.spring.template.notification.entity

import com.leijendary.spring.template.notification.core.entity.IdentityEntity
import com.leijendary.spring.template.notification.core.util.RequestContext.now
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import org.springframework.data.annotation.CreatedDate
import java.time.OffsetDateTime
import java.util.*

@Entity
class Device : IdentityEntity() {
    enum class Platform(val value: String) {
        IOS("ios"),
        ANDROID("android"),
        WEB("web");
    }

    lateinit var userId: UUID
    lateinit var token: String

    @Enumerated(STRING)
    lateinit var platform: Platform

    var endpoint: String = ""

    @CreatedDate
    var createdAt: OffsetDateTime = now
}
