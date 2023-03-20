package com.leijendary.spring.template.notification.api.v1.model

import jakarta.validation.constraints.NotBlank

data class DeviceDeregisterRequest(
    @field:NotBlank(message = "validation.required")
    val token: String? = null
)
