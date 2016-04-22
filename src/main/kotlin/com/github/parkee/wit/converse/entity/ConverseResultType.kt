package com.github.parkee.wit.converse.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class ConverseResultType(private val type: String) {
    MERGE("merge"), // first bot action after a user message
    MSG("msg"), // the bot has something to say
    ACTION("action"), // the bot has something to do
    ERROR("error"),
    STOP("stop"); // the bot is waiting to proceed

    @JsonCreator
    fun fromString(type: String?): ConverseResultType? {
        type ?: return null
        return ConverseResultType.valueOf(type)
    }

    @JsonValue
    override fun toString(): String {
        return type
    }
}