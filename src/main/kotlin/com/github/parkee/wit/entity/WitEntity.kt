package com.github.parkee.wit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.github.parkee.wit.json.fromJsonTo
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
    fun valueAsString() = value
    fun valueAsByte() = value.toByte()
    fun valueAsBoolean() = value.toBoolean()
    fun valueAsShort() = value.toShort()
    fun valueAsInteger() = value.toInt()
    fun valueAsLong() = value.toLong()
    fun valueAsFloat() = value.toFloat()
    fun valueAsDouble() = value.toDouble()
    fun valueAsZonedDateTime() = ZonedDateTime.parse(value)
    fun valueAsEntity(): WitEntity = value.fromJsonTo(WitEntity::class)
    
    fun valueAsZonedDateTimeRange(): Pair<ZonedDateTime, ZonedDateTime> {
        val tr = object : TypeReference<Map<String, String>>() {}
        val map: Map<String, String> = value.fromJsonTo(tr)
        val fromDateTime = ZonedDateTime.parse(map["from"])
        val toDateTime = ZonedDateTime.parse(map["to"])
        return fromDateTime to toDateTime
    }
}
