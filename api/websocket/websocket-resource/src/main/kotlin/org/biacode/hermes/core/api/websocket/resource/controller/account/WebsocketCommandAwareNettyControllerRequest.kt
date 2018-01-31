package org.biacode.hermes.core.api.websocket.resource.controller.account

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 1:21 AM
 */
open class WebsocketCommandAwareNettyControllerRequest(
        @JsonProperty("command")
        var command: String? = null
)