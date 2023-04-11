package com.leijendary.spring.template.notification.client

import com.leijendary.spring.template.notification.core.config.properties.AwsSnsProperties
import com.leijendary.spring.template.notification.core.extension.toJson
import com.leijendary.spring.template.notification.core.util.SpringContext.Companion.isProd
import com.leijendary.spring.template.notification.entity.Device.Platform
import com.leijendary.spring.template.notification.entity.Device.Platform.ANDROID
import com.leijendary.spring.template.notification.entity.Device.Platform.IOS
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest

@Component
class NotificationClient(private val awsSnsProperties: AwsSnsProperties, private val snsClient: SnsClient) {
    fun createEndpoint(platform: Platform, token: String): String? {
        val arn = getArn(platform) ?: return null
        val response = snsClient.createPlatformEndpoint {
            it.platformApplicationArn(arn)
            it.token(token)
        }

        return response.endpointArn()
    }

    fun deleteEndpoint(endpoint: String) {
        snsClient.deleteEndpoint {
            it.endpointArn(endpoint)
        }
    }

    fun send(platform: Platform, token: String, title: String, body: String, imageUrl: String?) {
        val arn = getArn(platform) ?: return
        val message = when (platform) {
            ANDROID -> android(title, body, imageUrl)
            IOS -> ios(title, body, imageUrl)
            else -> return
        }
        val json = message.toJson()
        val request = PublishRequest.builder()
            .targetArn(arn)
            .message(json)
            .build()

        snsClient.publish(request)
    }

    private fun getArn(platform: Platform) = when (platform) {
        ANDROID -> awsSnsProperties.platform.firebase.arn
        IOS -> awsSnsProperties.platform.apple.arn
        else -> null
    }

    private fun android(title: String, body: String, imageUrl: String?): Map<String, String> {
        val notification = mutableMapOf(
            "title" to title,
            "body" to body
        )

        imageUrl?.let { notification.put("image", it) }

        val gcm = mapOf("notification" to notification)
        val json = gcm.toJson()
        val key = "GCM"

        return mapOf(key to json)
    }

    private fun ios(title: String, body: String, imageUrl: String?): Map<String, String> {
        val alert = mutableMapOf(
            "title" to title,
            "body" to body
        )

        imageUrl?.let { alert.put("url", it) }

        val aps = mapOf("alert" to alert)
        val apns = mapOf("aps" to aps)
        val json = apns.toJson()
        val key = if (isProd()) "APNS" else "APNS_SANDBOX"

        return mapOf(key to json)
    }
}
