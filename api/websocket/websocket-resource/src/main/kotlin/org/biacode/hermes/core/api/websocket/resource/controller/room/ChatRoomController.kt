package org.biacode.hermes.core.api.websocket.resource.controller.room

import org.biacode.spring.netty.core.annotation.NettyCommand
import org.biacode.spring.netty.core.annotation.NettyController

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 12:08 AM
 */
@NettyController
class ChatRoomController {

    @NettyCommand("room/create")
    fun create(request: CreateRoomRequest) {
        println(request)
    }

    @NettyCommand("room/send")
    fun sendMessageToRoom(request: SendMessageToRoomRequest) {
        println(request)
    }
}
