package com.github.parkee.wit

/**
 * Created by parkee on 4/20/16.
 */

class WitClient(val accessToken: String, val acceptHeader: String = "application/vnd.wit.20160330+json") {

    val headers = mapOf("authorization" to "Bearer $accessToken", "accept" to acceptHeader)


}