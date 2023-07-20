package com.leijendary.spring.template.notification.api.v1.rest

import com.leijendary.spring.template.notification.api.v1.model.DeviceDeregisterRequest
import com.leijendary.spring.template.notification.api.v1.model.DeviceRegisterRequest
import com.leijendary.spring.template.notification.api.v1.service.DeviceService
import com.leijendary.spring.template.notification.core.util.RequestContext.userIdOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/devices")
@Tag(name = "Device", description = "Device management APIs for the currently logged in user.")
class DeviceRest(private val deviceService: DeviceService) {
    @PostMapping
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Register the device for push notifications.")
    fun register(@Valid @RequestBody request: DeviceRegisterRequest) {
        deviceService.register(userIdOrThrow, request)
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deregister the device and exclude from push notifications.")
    fun deregister(@Valid @RequestBody request: DeviceDeregisterRequest) {
        deviceService.deregister(userIdOrThrow, request)
    }
}
