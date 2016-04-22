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
        private val sayFunction: Function3<String, Map<String, Any>, String, Unit>,
        // session_id, context, entities, msg -> context
        private val mergeFunction: Function4<String, Map<String, Any>, Map<String, List<WitEntity>>, String, Map<String, Any>>,
        // session_id, context, exception -> void
        private val errorFunction: Function3<String, Map<String, Any>, Throwable, Unit>,
        // session_id, context -> context
        private val userDefinedFunctions: Map<String, Function2<String, Map<String, Any>, Map<String, Any>>>,
        private val intentTemplate: WitIntentTemplate = WitIntentTemplate(accessToken, acceptHeader),
        private val converseTemplate: WitConverseTemplate = WitConverseTemplate(accessToken, acceptHeader)
) : WitIntentOperations by intentTemplate, WitConverseOperations by converseTemplate {

    fun runActions(sessionId: String,
                   message: String,
                   context: Map<String, Any> = emptyMap(),
                   maxSteps: Int = 5): Map<String, Any> {

        return runActions(sessionId, message, context, maxSteps, message)
    }

    private fun runActions(sessionId: String,
                   message: String?,
                   context: Map<String, Any> = emptyMap(),
                   maxSteps: Int = 5,
                   userMessage: String? = message): Map<String, Any> {

        var newContext: Map<String, Any> = mutableMapOf<String, Any>().apply { putAll(context) }

        if (maxSteps <= 0) throw WitException("Max iterations reached")

        val converseResult = converse(sessionId, message, newContext)

        when (converseResult.type) {
            ConverseResultType.STOP -> return newContext

            ConverseResultType.MSG -> {
                sayFunction.invoke(sessionId, newContext, converseResult.message.orEmpty())
            }

            ConverseResultType.MERGE -> {
                newContext = mergeFunction.invoke(sessionId, newContext, converseResult.entities.orEmpty(), converseResult.message.orEmpty())
            }

            ConverseResultType.ACTION -> {
                newContext = userDefinedFunctions[converseResult.action]?.invoke(sessionId, newContext).orEmpty()
            }

            ConverseResultType.ERROR -> {
                errorFunction(sessionId, newContext, WitException("Oops, I don't know wit to do"))
            }

            else -> WitException("Unknown ConverseResultType") // should never happen
        }

        return runActions(sessionId, null, newContext, maxSteps - 1, userMessage)
    }
}
