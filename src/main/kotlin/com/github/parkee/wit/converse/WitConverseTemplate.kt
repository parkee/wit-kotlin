package com.github.parkee.wit.converse

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.parkee.wit.converse.entity.WitConverseResult
import com.github.parkee.wit.exception.WitException

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
                          context: Map<String, String>?): WitConverseResult {

        val parameters = listOf(
                "session_id" to sessionId,
                "q" to query,
                "context" to context)

        val (request, response, result) = Fuel.post("$BASE_URL?${queryFromParameters(parameters)}")
                .header(headers)
                .responseString()

        val (successResponse, errorResponse) = result

        val errorData: ByteArray? = errorResponse?.errorData
        if (errorData != null) {
            throw WitException("Error with code ${response.httpStatusCode} and body ${String(errorData)}")
        }

        return jacksonObjectMapper().readValue(successResponse, WitConverseResult::class.java)
    }

    private fun queryFromParameters(params: List<Pair<String, Any?>>?): String {
        return params?.let {
            params.filterNot { it.second == null }
                    .mapTo(arrayListOf<String>()) { "${it.first}=${it.second}" }
                    .joinToString("&")
        } ?: ""
    }
}

