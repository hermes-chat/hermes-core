package org.biacode.hermes.core.api.websocket.resource.controller.room

import com.fasterxml.jackson.annotation.JsonProperty
import org.biacode.hermes.spring.netty.model.WebsocketCommandAwareNettyControllerRequest

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 12:12 AM
 */
class SendMessageToRoomRequest(
        @JsonProperty("message")
        val message: String
) : WebsocketCommandAwareNettyControllerRequest()