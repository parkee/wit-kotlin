package com.github.parkee.wit

import com.github.parkee.wit.converse.WitConverseOperations
import com.github.parkee.wit.converse.WitConverseTemplate
import com.github.parkee.wit.converse.entity.ConverseResultType
import com.github.parkee.wit.entity.WitEntity
import com.github.parkee.wit.exception.WitException
import com.github.parkee.wit.intent.WitIntentOperations
import com.github.parkee.wit.intent.WitIntentTemplate

/**
 * Created by parkee on 4/20/16.
 */
class WitClient(
        val accessToken: String,
        val acceptHeader: String = "application/vnd.wit.20160330+json",
        // session_id, context, msg -> void
        private val sayFunction: Function3<String, Map<String, String>, String, Unit>,
        // session_id, context, entities, msg -> context
        private val mergeFunction: Function4<String, Map<String, String>, List<WitEntity>, String, Map<String, String>>,
        // session_id, context, exception -> void
        private val errorFunction: Function3<String, Map<String, String>, Throwable, Unit>,
        // session_id, context -> context
        private val userDefinedFunctions: Map<String, Function2<String, Map<String, String>, Map<String, String>>>,
        private val intentTemplate: WitIntentTemplate = WitIntentTemplate(accessToken, acceptHeader),
        private val converseTemplate: WitConverseTemplate = WitConverseTemplate(accessToken, acceptHeader)
) : WitIntentOperations by intentTemplate, WitConverseOperations by converseTemplate {
    fun runActions(sessionId: String,
                   message: String,
                   context: Map<String, String> = emptyMap(),
                   maxSteps: Int = 5,
                   userMessage: String = message): Map<String, String> {

        if (maxSteps <= 0) throw WitException("Max iterations reached")

        val converseResult = converse(sessionId, message, context)

        when (converseResult.type) {
            ConverseResultType.STOP -> return context
            ConverseResultType.MSG -> {}
            ConverseResultType.MERGE -> {}
            ConverseResultType.ACTION -> {}
        }

        return null!!
    }
}
