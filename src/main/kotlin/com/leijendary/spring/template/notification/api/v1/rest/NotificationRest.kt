package com.leijendary.spring.template.notification.api.v1.rest

import com.leijendary.spring.template.notification.api.v1.service.NotificationService
import com.leijendary.spring.template.notification.core.util.RequestContext.userIdOrThrow
import com.leijendary.spring.template.notification.entity.Notification.Status
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notification", description = "Push notifications of the currently logged in user.")
class NotificationRest(private val notificationService: NotificationService) {
    @GetMapping
    @Operation(summary = "Get the paginated list of the user's push notifications.")
    fun list(pageable: Pageable) = notificationService.list(userIdOrThrow, pageable)

    @GetMapping("{id}")
    @Operation(
        summary = """
            Retrieves the push notification details from the database. 
            This also marks the notification as read.
        """
    )
    fun get(@PathVariable id: UUID) = notificationService.get(userIdOrThrow, id)

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete the notification from the database.")
    fun delete(@PathVariable id: UUID) = notificationService.delete(userIdOrThrow, id)

    @GetMapping("count")
    @Operation(summary = "Counts the number of notifications based on the status")
    fun count(status: Status) = notificationService.count(userIdOrThrow, status)
}
