package com.leijendary.spring.template.notification.core.config.properties

import com.zaxxer.hikari.HikariConfig
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.datasource.readonly")
class DataSourceReadonlyProperties : HikariConfig()
