package com.leijendary.spring.template.notification

import org.springframework.boot.SpringBootVersion
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.core.env.get
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        setBanner { environment, _, out ->
            val name = environment["info.app.name"]
            val version = environment["info.app.version"]
            val springVersion = SpringBootVersion.getVersion()

            out.print("Running $name v$version on Spring Boot v$springVersion")
        }
    }
}
