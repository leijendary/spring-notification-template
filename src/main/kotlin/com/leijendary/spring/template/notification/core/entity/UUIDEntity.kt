package com.leijendary.spring.template.notification.core.entity

import com.leijendary.spring.template.notification.core.projection.UUIDProjection
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.util.*

@MappedSuperclass
open class UUIDEntity : UUIDProjection {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    override var id: UUID? = null

    @Column(insertable = false, updatable = false)
    var rowId: Long? = null
}
