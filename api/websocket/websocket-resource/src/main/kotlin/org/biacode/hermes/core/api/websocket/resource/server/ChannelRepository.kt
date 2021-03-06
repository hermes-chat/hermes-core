package org.biacode.hermes.core.api.websocket.resource.server

import io.netty.channel.Channel
import io.netty.channel.group.ChannelGroup
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.DefaultEventExecutor
import io.netty.util.concurrent.EventExecutor
import io.netty.util.internal.PlatformDependent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentMap
import javax.annotation.PostConstruct

/**
 * Created by Arthur Asatryan.
 * Date: 1/23/18
 * Time: 12:43 AM
 */
@Component
class ChannelRepository {

    //region Properties
    private var rooms: ConcurrentMap<String, ChannelGroup> = PlatformDependent.newConcurrentHashMap<String, ChannelGroup>()
    //endregion

    //region Public methods
    fun createRoom(name: String): DefaultChannelGroup {
        logger.debug("creating room with name - {}", name)
        val defaultChannelGroup = DefaultChannelGroup(channelGroupEventExecutor)
//        removeOnChannelGroupClose(defaultChannelGroup, name)
        rooms.putIfAbsent(name, defaultChannelGroup)
        return defaultChannelGroup
    }

    fun addChannelToRoom(roomName: String, channel: Channel) {
        // TODO: ???
        rooms[roomName]?.add(channel)
    }

    fun findChannelGroup(roomName: String): Optional<ChannelGroup> {
        return Optional.ofNullable(rooms[roomName])
    }

    @PostConstruct
    fun postConstruct() {
        val defaultChannelGroup = DefaultChannelGroup(channelGroupEventExecutor)
//        removeOnChannelGroupClose(defaultChannelGroup, DEFAULT_ROOM_NAME)
        rooms[DEFAULT_ROOM_NAME] = defaultChannelGroup
    }
    //endregion

    //region Utility methods
    private fun removeOnChannelGroupClose(defaultChannelGroup: DefaultChannelGroup, name: String) {
        defaultChannelGroup.newCloseFuture().addListener { rooms.remove(name) }
    }
    //endregion

    //region Companion object
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ChannelRepository::class.java)

        const val DEFAULT_ROOM_NAME = "guest"

        private val channelGroupEventExecutor: EventExecutor = DefaultEventExecutor()
    }
    //endregion
}