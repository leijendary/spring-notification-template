package com.leijendary.spring.template.notification.model

enum class Platform(val value: String) {
    IOS("ios"),
    ANDROID("android"),
    WEB("web");

    companion object {
        fun from(value: String) = values().first { it.value == value }
    }

    override fun toString() = value
}
