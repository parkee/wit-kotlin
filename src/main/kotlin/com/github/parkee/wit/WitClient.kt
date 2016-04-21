package com.github.parkee.wit

import com.github.parkee.wit.intent.WitIntentOperations
import com.github.parkee.wit.intent.WitIntentTemplate

/**
 * Created by parkee on 4/20/16.
 */
class WitClient(
        val accessToken: String,
        val acceptHeader: String = "application/vnd.wit.20160330+json",
        var intentTemplate: WitIntentTemplate = WitIntentTemplate(accessToken, acceptHeader)
) : WitIntentOperations by intentTemplate