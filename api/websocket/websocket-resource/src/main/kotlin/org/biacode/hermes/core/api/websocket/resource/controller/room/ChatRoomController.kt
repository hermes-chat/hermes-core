package org.biacode.hermes.core.api.websocket.resource.controller.room

import org.biacode.hermes.core.api.websocket.resource.configuration.annotation.WebsocketCommand
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 1/25/18
 * Time: 3:48 PM
 */
@Component
class ChatRoomController {

    //region Dependencies
    //endregion

    //region Public methods
    @WebsocketCommand("room/addRoom")
    fun createRoom(name: String) {
        println("hahahha")
    }
    //endregion

}