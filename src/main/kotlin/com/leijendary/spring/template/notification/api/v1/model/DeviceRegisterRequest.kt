package com.leijendary.spring.template.notification.api.v1.model

import com.leijendary.spring.template.notification.core.validator.annotation.EnumField
import com.leijendary.spring.template.notification.model.Platform
import jakarta.validation.constraints.NotBlank

data class DeviceRegisterRequest(
    @field:NotBlank(message = "validation.required")
    val token: String? = null,

    @field:NotBlank(message = "validation.required")
    @field:EnumField(enumClass = Platform::class)
    val platform: String? = null,
)
