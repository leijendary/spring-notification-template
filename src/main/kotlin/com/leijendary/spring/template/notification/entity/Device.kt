package com.leijendary.spring.template.notification.entity

import com.leijendary.spring.template.notification.core.entity.IdentityEntity
import com.leijendary.spring.template.notification.model.Platform
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import org.springframework.data.annotation.CreatedDate
import java.time.OffsetDateTime
import java.util.*

@Entity
class Device : IdentityEntity() {
    lateinit var userId: UUID
    lateinit var token: String

    @Enumerated(STRING)
    lateinit var platform: Platform

    lateinit var endpoint: String

    @CreatedDate
    lateinit var createdAt: OffsetDateTime
}
