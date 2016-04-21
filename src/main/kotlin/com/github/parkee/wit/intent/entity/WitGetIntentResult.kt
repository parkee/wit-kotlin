package com.github.parkee.wit.intent.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class WitGetIntentResult(
        @JsonProperty("msg_id") val messageId: String,
        @JsonProperty("_text") val text: String,
        @JsonProperty("outcomes") val outcomes: List<WitOutcome>
)