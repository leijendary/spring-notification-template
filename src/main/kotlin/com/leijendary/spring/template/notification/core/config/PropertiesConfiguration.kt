package com.leijendary.spring.template.notification.core.config

import com.leijendary.spring.template.notification.core.config.properties.*
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    AuthProperties::class,
    AwsSnsProperties::class,
    DataSourcePrimaryProperties::class,
    DataSourceReadonlyProperties::class,
    InfoProperties::class,
    KafkaTopicProperties::class,
    TwilioProperties::class
)
class PropertiesConfiguration 
