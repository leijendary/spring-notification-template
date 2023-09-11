package com.leijendary.spring.template.notification.core.model

import java.io.Serializable

sealed interface Response : Serializable {
    companion object {
        @JvmStatic
        val serialVersionUID: Long = -1L
    }
}
