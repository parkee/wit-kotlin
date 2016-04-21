package com.github.parkee.wit.intent.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class WitOutcome(
        @JsonProperty("_text") val text: String,
        @JsonProperty("intent") val intent: String,
        @JsonProperty("entities") val entities: Map<String, List<WitEntity>> = emptyMap(),
        @JsonProperty("confidence") val confidence: Double?
)