package com.leijendary.spring.template.notification.core.mapper

import com.leijendary.spring.template.notification.core.util.RequestContext.zoneId
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers.getMapper
import java.time.Instant
import java.time.OffsetDateTime

@Mapper
interface DateMapper {
    companion object {
        val INSTANCE: DateMapper = getMapper(DateMapper::class.java)
    }

    fun toOffsetDateTime(epochMilli: Long): OffsetDateTime {
        return Instant
            .ofEpochMilli(epochMilli)
            .atZone(zoneId)
            .toOffsetDateTime()
    }

    fun toEpochMilli(offsetDateTime: OffsetDateTime): Long = offsetDateTime.toInstant().toEpochMilli()
}
