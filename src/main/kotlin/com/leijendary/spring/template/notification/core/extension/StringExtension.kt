package com.leijendary.spring.template.notification.core.extension

import com.leijendary.spring.template.notification.core.util.BeanContainer.objectMapper
import java.lang.Character.toLowerCase
import java.lang.Character.toUpperCase

inline fun <reified T : Any> String.toClass(): T {
    return objectMapper.readValue(this, T::class.java)
}

fun <T> String.toClass(reference: TypeReference<T>): T {
    return objectMapper.readValue(this, reference)
}

fun String.snakeCaseToCamelCase(capitalizeFirst: Boolean = false): String {
    val builder = StringBuilder()

    split("_".toRegex()).toTypedArray().forEach { builder.append(it.upperCaseFirst()) }

    val result = builder.toString()

    return if (!capitalizeFirst) result.lowerCaseFirst() else result
}

fun String.upperCaseFirst(): String {
    val chars = toCharArray()
    chars[0] = toUpperCase(chars[0])

    return String(chars)
}

fun String.lowerCaseFirst(): String {
    val chars = toCharArray()
    chars[0] = toLowerCase(chars[0])

    return String(chars)
}
