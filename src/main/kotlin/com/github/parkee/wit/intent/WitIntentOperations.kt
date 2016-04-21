package com.github.parkee.wit.intent

import com.github.parkee.wit.intent.entity.WitGetIntentResult

interface WitIntentOperations {
    fun getSentenceMeaning(message: String): WitGetIntentResult
}