package org.biacode.hermes.core.api.websocket.resource.controller.common

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 12:20 AM
 */
enum class WebsocketCommandType(private val command: String) {

    //region Properties
    @JsonProperty("room/create")
    ROOM_CREATE_ROOM("room/create"),

    @JsonProperty("account/create")
    ACCOUNT_CREATE_ACCOUNT("account/create");
    //endregion

    //region Companion objects
    companion object {
        fun getValueFor(command: String): WebsocketCommandType {
            return WebsocketCommandType.values().first { it.command == command }
        }
    }
    //endregion
}
