plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.barfuin.gradle.jacocolog") version "3.1.0"
    kotlin("jvm") version "1.8.10"
    kotlin("kapt") version "1.8.10"
    kotlin("plugin.spring") version "1.8.10"
    kotlin("plugin.jpa") version "1.8.10"
}

group = "com.leijendary.spring"
version = "1.0.0"
description = "Spring Boot Notification Template for the Microservice Architecture or general purpose"
java.sourceCompatibility = JavaVersion.VERSION_19

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    testImplementation {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

repositories {
    mavenCentral()
}

kapt {
    arguments {
        arg("mapstruct.unmappedTargetPolicy", "IGNORE")
    }
    javacOptions {
        option("--enable-preview")
    }
}

dependencies {
    // Kotlin
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))

    // Kotlinx
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    // Spring Boot Starter
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Spring Retry
    implementation("org.springframework.retry:spring-retry")

    // Spring Cloud
    implementation("org.springframework.cloud:spring-cloud-context")

    // Spring Kafka
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")

    // AWS
    implementation("io.awspring.cloud:spring-cloud-aws-starter")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-ses")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sns")

    // Database
    implementation("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")
    testImplementation("org.mapstruct:mapstruct-processor:1.5.3.Final")

    // Devtools
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")

    // Tracing
    implementation("com.github.loki4j:loki-logback-appender:1.4.0")
    implementation("io.github.openfeign:feign-micrometer")
    implementation("io.micrometer:micrometer-observation")
    implementation("io.micrometer:micrometer-tracing-bridge-otel")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.opentelemetry:opentelemetry-exporter-zipkin")
    implementation("net.ttddyy.observation:datasource-micrometer-spring-boot:1.0.1")

    // Test
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")

    // Test Containers
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka")
    testImplementation("org.testcontainers:postgresql")
}

dependencyManagement {
    imports {
        mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.0-RC1")
        mavenBom("io.micrometer:micrometer-tracing-bom:1.0.1")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.1")
        mavenBom("org.testcontainers:testcontainers-bom:1.17.6")
    }
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all", "-Xjvm-enable-preview")
            jvmTarget = "19"
        }
    }

    compileJava {
        options.compilerArgs.add("--enable-preview")
    }

    bootJar {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    jar {
        enabled = false
    }

    test {
        jvmArgs = listOf("--enable-preview")
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)
        finalizedBy(jacocoTestCoverageVerification)
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                element = "CLASS"
                includes = listOf("${project.group}.*")

                limit {
                    counter = "LINE"
                    minimum = "1.0".toBigDecimal()
                }

                limit {
                    counter = "BRANCH"
                    minimum = "1.0".toBigDecimal()
                }
            }
        }
    }

    processResources {
        filesMatching("application.yaml") {
            expand(project.properties)
        }
    }
}
