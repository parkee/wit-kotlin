package com.github.parkee.wit.json

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.reflect.KClass

/**
 * Created by parkee on 4/22/16.
 */
fun Any.toJsonAsString(): String {
    return jacksonObjectMapper().writeValueAsString(this)
}

fun Any.toJsonAsBytes(): ByteArray {
    return jacksonObjectMapper().writeValueAsBytes(this)
}

fun <T> String.fromJsonTo(clazz: Class<T>): T {
    return jacksonObjectMapper().readValue(this, clazz)
}

fun <T : Any> String.fromJsonTo(clazz: KClass<T>): T {
    return jacksonObjectMapper().readValue(this, clazz.java)
}

fun <T> String.fromJsonTo(typeReference: TypeReference<T>): T {
    return jacksonObjectMapper().readValue(this, typeReference)
}