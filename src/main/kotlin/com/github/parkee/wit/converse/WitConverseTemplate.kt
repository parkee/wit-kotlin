package com.github.parkee.wit.converse

import com.github.kittinunf.fuel.Fuel
import com.github.parkee.wit.converse.entity.WitConverseResult
import com.github.parkee.wit.exception.WitException
import com.github.parkee.wit.json.fromJsonTo
import com.github.parkee.wit.json.toJsonAsBytes

/**
 * Created by parkee on 4/21/16.
 */
class WitConverseTemplate(
        val accessToken: String,
        val acceptHeader: String = "application/vnd.wit.20160330+json") : WitConverseOperations {

    private val headers = mapOf("authorization" to "Bearer $accessToken", "accept" to acceptHeader)

    private val BASE_URL = "https://api.wit.ai/converse"

    override fun converse(sessionId: String,
                          query: String?,
                          context: Map<String, Any>): WitConverseResult {

        val serializedContext = context.toJsonAsBytes()
        val parameters = listOf(
                "session_id" to sessionId,
                "q" to query)

        val (request, response, result) = Fuel.post("$BASE_URL?${queryFromParameters(parameters)}")
                .header(headers)
                .body(serializedContext)
                .responseString()

        val (successResponse, errorResponse) = result

        val errorData: ByteArray? = errorResponse?.errorData
        if (errorData != null) {
            throw WitException("Error with code ${response.httpStatusCode} and body ${String(errorData)}")
        }

        return successResponse?.fromJsonTo(WitConverseResult::class) ?: throw WitException("Wit returned empty result")
    }

    private fun queryFromParameters(params: List<Pair<String, Any?>>?): String {
        return params?.let {
            params.filterNot { it.second == null }
                    .mapTo(arrayListOf<String>()) { "${it.first}=${it.second}" }
                    .joinToString("&")
        } ?: ""
    }
}

