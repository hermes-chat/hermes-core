package org.biacode.hermes.core.api.websocket.resource.controller.common

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 1:06 AM
 */
open class CommandAwareWebsocketRequest(
        @JsonProperty("command")
        val command: WebsocketCommandType? = null
) : WebsocketRequest