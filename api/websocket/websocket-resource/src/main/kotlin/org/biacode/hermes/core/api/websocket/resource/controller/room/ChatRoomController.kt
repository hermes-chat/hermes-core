package org.biacode.hermes.core.api.websocket.resource.controller.room

import org.biacode.hermes.core.api.websocket.resource.configuration.annotation.WebsocketCommand
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 12:08 AM
 */
@Component
class ChatRoomController {

    @WebsocketCommand("room/create")
    fun create(request: CreateRoomRequest) {
        println(request)
    }
}
