package com.github.parkee.wit.intent

import com.github.kittinunf.fuel.Fuel
import com.github.parkee.wit.exception.WitException
import com.github.parkee.wit.intent.entity.WitGetIntentResult
import com.github.parkee.wit.json.fromJsonTo

/**
 * Created by parkee on 4/21/16.
 */
class WitIntentTemplate(
        val accessToken: String, val acceptHeader: String = "application/vnd.wit.20160330+json") : WitIntentOperations {

    private val headers = mapOf("authorization" to "Bearer $accessToken", "accept" to acceptHeader)

    private val BASE_URL = "https://api.wit.ai/message"

    override fun getSentenceMeaning(query: String,
                                    context: Map<String, Any>?,
                                    messageId: String?,
                                    threadId: String?,
                                    numberOfBestOutcomes: Int?): WitGetIntentResult {

        if (!(query.length > 0 && query.length < 256)) {
            throw IllegalArgumentException("User’s query length must be > 0 and < 256, but was ${query.length}")
        }

        val parameters = listOf(
                "q" to query,
                "context" to context,
                "msg_id" to messageId,
                "thread_id" to threadId,
                "n" to numberOfBestOutcomes)

        val (request, response, result) = Fuel.get(BASE_URL, parameters)
                .header(headers)
                .responseString()

        val (successResponse, errorResponse) = result

        val errorData: ByteArray? = errorResponse?.errorData
        if (errorData != null) {
            throw WitException("Error with code ${response.httpStatusCode} and body ${String(errorData)}")
        }

        return successResponse?.fromJsonTo(WitGetIntentResult::class) ?: throw WitException("Wit returned empty result")
    }
}

