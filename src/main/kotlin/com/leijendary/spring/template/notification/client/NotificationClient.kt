package com.leijendary.spring.template.notification.client

import com.leijendary.spring.template.notification.core.config.properties.AwsSnsProperties
import com.leijendary.spring.template.notification.core.extension.logger
import com.leijendary.spring.template.notification.core.extension.toJson
import com.leijendary.spring.template.notification.core.util.SpringContext.Companion.isProd
import com.leijendary.spring.template.notification.entity.Device.Platform
import com.leijendary.spring.template.notification.entity.Device.Platform.ANDROID
import com.leijendary.spring.template.notification.entity.Device.Platform.IOS
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.NotFoundException
import software.amazon.awssdk.services.sns.model.PublishRequest
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletableFuture.completedFuture
import java.util.concurrent.CompletableFuture.failedFuture

@Component
class NotificationClient(private val awsSnsProperties: AwsSnsProperties, private val snsClient: SnsClient) {
    private val log = logger()

    fun createEndpoint(platform: Platform, token: String): String? {
        val arn = getTopicArn(platform) ?: return null
        val response = snsClient.createPlatformEndpoint {
            it.platformApplicationArn(arn)
            it.token(token)
        }

        return response.endpointArn()
    }

    fun deleteEndpoint(endpoint: String) {
        snsClient.deleteEndpoint { it.endpointArn(endpoint) }
    }

    @Async
    fun send(
        platform: Platform,
        endpoint: String,
        title: String,
        body: String,
        image: String?
    ): CompletableFuture<Void> {
        val topicArn = getTopicArn(platform) ?: return completedFuture(null)
        val message = when (platform) {
            ANDROID -> android(title, body, image)
            IOS -> ios(title, body, image)
            else -> return completedFuture(null)
        }
        val json = message.toJson()
        val request = PublishRequest.builder()
            .topicArn(topicArn)
            .targetArn(endpoint)
            .message(json)
            .build()

        try {
            snsClient.publish(request)
        } catch (exception: SdkException) {
            when (exception) {
                is NotFoundException -> return failedFuture(exception)

                else -> log.error("Error when sending to endpoint $endpoint", exception)
            }
        }

        return completedFuture(null)
    }

    private fun getTopicArn(platform: Platform) = when (platform) {
        ANDROID -> awsSnsProperties.platform.firebase.arn
        IOS -> awsSnsProperties.platform.ios.arn
        else -> null
    }

    private fun android(title: String, body: String, image: String?): Map<String, String> {
        val notification = mutableMapOf(
            "title" to title,
            "body" to body
        )

        image?.let { notification.put("image", it) }

        val gcm = mapOf("notification" to notification)
        val json = gcm.toJson()
        val key = "GCM"

        return mapOf(key to json)
    }

    private fun ios(title: String, body: String, image: String?): Map<String, String> {
        val alert = mutableMapOf(
            "title" to title,
            "body" to body
        )

        image?.let { alert.put("url", it) }

        val aps = mapOf("alert" to alert)
        val apns = mapOf("aps" to aps)
        val json = apns.toJson()
        val key = if (isProd()) "APNS" else "APNS_SANDBOX"

        return mapOf(key to json)
    }
}
