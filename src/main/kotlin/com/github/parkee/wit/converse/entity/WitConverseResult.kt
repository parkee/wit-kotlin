package com.github.parkee.wit.converse.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.parkee.wit.entity.WitEntity

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class WitConverseResult(
        @JsonProperty("type") val type: ConverseResultType,
        @JsonProperty("msg") val message: String?,
        @JsonProperty("action") val action: String?,
        @JsonProperty("entities") val entities: Map<String, List<WitEntity>>?,
        @JsonProperty("confidence") val confidence: Double?
)