package org.biacode.hermes.core.api.websocket.resource.controller.room

import org.biacode.hermes.spring.netty.core.annotation.HermesCommand
import org.biacode.hermes.spring.netty.core.annotation.HermesController

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 12:08 AM
 */
@HermesController
class ChatRoomController {

    @HermesCommand("room/create")
    fun create(request: CreateRoomRequest) {
        println(request)
    }

    @HermesCommand("room/send")
    fun sendMessageToRoom(request: SendMessageToRoomRequest) {
        println(request)
    }
}
