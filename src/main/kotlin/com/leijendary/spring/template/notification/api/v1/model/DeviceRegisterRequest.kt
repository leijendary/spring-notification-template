package com.leijendary.spring.template.notification.api.v1.model

import com.leijendary.spring.template.notification.model.Platform
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class DeviceRegisterRequest(
    @field:NotBlank(message = "validation.required")
    val token: String? = null,

    @field:NotNull(message = "validation.required")
    val platform: Platform? = null,
)
