package com.github.parkee.wit.intent

import com.github.parkee.wit.intent.entity.WitGetIntentResult

interface WitIntentOperations {
    fun getSentenceMeaning(query: String,
                           context: String? = null,
                           messageId: String? = null,
                           threadId: String? = null,
                           numberOfBestOutcomes: Int? = null): WitGetIntentResult
}