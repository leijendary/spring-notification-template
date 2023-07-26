package com.leijendary.spring.template.notification.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.cloud.aws.sns")
class AwsSnsProperties {
    var platform = Platform()

    inner class Platform {
        var firebase = Config()
        var ios = Config()
    }

    inner class Config {
        lateinit var arn: String
    }
}
