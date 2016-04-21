package com.github.parkee.wit.converse

import com.github.parkee.wit.converse.entity.WitConverseResult

interface WitConverseOperations {
    fun converse(sessionId: String,
                 query: String? = null,
                 context: String? = null): WitConverseResult
}