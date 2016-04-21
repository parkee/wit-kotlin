package com.github.parkee.wit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class WitEntity(
        @JsonProperty("metadata") val metadata: String? = "",
        @JsonProperty("type") val type: String? = "",
        @JsonProperty("suggested") val suggested: Boolean? = false,
        @JsonProperty("value") val value: String,
        @JsonProperty("body") val body: String?,
        @JsonProperty("start") val start: Int?,
        @JsonProperty("end") val end: Int?,
        @JsonProperty("entity") val entity: String?
) {
    fun getValueAsString() = value
    fun getValueAsByte() = value.toByte()
    fun getValueAsBoolean() = value.toBoolean()
    fun getValueAsShort() = value.toShort()
    fun getValueAsInteger() = value.toInt()
    fun getValueAsLong() = value.toLong()
    fun getValueAsFloat() = value.toFloat()
    fun getValueAsDouble() = value.toDouble()
    fun getValueAsZonedDateTime() = ZonedDateTime.parse(value)
    fun getValueAsEntity(): WitEntity = jacksonObjectMapper().readValue(value, WitEntity::class.java)

    fun getValueAsZonedDateTimeRange(): Pair<ZonedDateTime, ZonedDateTime> {
        val tr = object : TypeReference<Map<String, String>>() {}
        val map: Map<String, String> = jacksonObjectMapper().readValue(value, tr)
        val fromDateTime = ZonedDateTime.parse(map["from"])
        val toDateTime = ZonedDateTime.parse(map["to"])
        return fromDateTime to toDateTime
    }
}