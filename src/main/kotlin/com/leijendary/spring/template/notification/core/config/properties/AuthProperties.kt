package com.leijendary.spring.template.notification.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.*

@ConfigurationProperties("auth")
class AuthProperties {
    var system = System()

    inner class System {
        var principal: String = ""
    }
}
