package com.github.parkee.wit.intent

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.parkee.wit.intent.entity.WitGetIntentResult

/**
 * Created by parkee on 4/21/16.
 */
class WitIntentTemplate(
        val accessToken: String, val acceptHeader: String = "application/vnd.wit.20160330+json") : WitIntentOperations {

    val headers = mapOf("authorization" to "Bearer $accessToken", "accept" to acceptHeader)

    private val BASE_URL = "https://api.wit.ai/message"

    override fun getSentenceMeaning(message: String): WitGetIntentResult {
        val (request, response, result) = Fuel.get(BASE_URL, listOf("q" to message))
                .header(headers)
                .responseString()
        val (successResponse, errorResponse) = result

        val errorData: ByteArray? = errorResponse?.errorData
        if (errorData != null) {
            throw RuntimeException(String(errorData))
        }

        return jacksonObjectMapper().readValue(successResponse, WitGetIntentResult::class.java)
    }
}