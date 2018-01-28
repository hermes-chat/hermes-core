package org.biacode.hermes.core.api.websocket.resource.controller.room

import org.biacode.hermes.spring.netty.config.annotation.NettyCommand
import org.biacode.hermes.spring.netty.config.annotation.NettyController

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
}
